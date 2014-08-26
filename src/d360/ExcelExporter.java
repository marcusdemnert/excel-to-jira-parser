package d360;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import d360.config.Configurator;
import d360.config.FieldConfig;
import d360.config.RowConfig;
import d360.config.SheetConfig;

/**
 * 
 * @author Marcus Demnert, @marcusdemnert
 * 
 */
public abstract class ExcelExporter {

    private final static Logger LOGGER = Logger.getLogger(ExcelExporter.class
            .getName());

    private static final String COLUMN_COUNTER_SEPARATOR = "@@";

    private static final Pattern COLUMN_COUNTER_PATTERN = Pattern
            .compile("(\\S)" + COLUMN_COUNTER_SEPARATOR + "\\d+");

    protected abstract String getOutputFileName();

    protected abstract String getInputFileName();

    protected abstract Configurator getConfigurator();

    /**
     * Gets the workbook to parse.
     * 
     * @return the workbook to parse.
     */
    protected Workbook getWorkbook() {
        Workbook book = null;
        try {
            book = new XSSFWorkbook(
                    ExcelExporter.class.getResourceAsStream(getInputFileName()));
        }
        catch (IOException e) {
            throw new RuntimeException("Could not load resource '"
                    + getInputFileName() + "'", e);
        }
        return book;
    }

    /**
     * Prettifies the given string be making everything lower case and then
     * upper case the first character in each sentence.
     *
     * @param value
     *            the value to prettify.
     * @return the prettified value.
     */
    private String toTitleCase(String value) {

        // Consider using the Guava Splitter instead!
        String[] arr = value.trim().split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equalsIgnoreCase("SAS")) {
                sb.append(arr[i].toUpperCase()).append(" ");
            }
            else {
                sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append(" ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Removes the optional counter part of the stored column name for multi
     * valued fields.
     * 
     * @param data
     * @return
     */
    private List<String> removeCounterFromColumnName(Set<String> columnNames) {
        List<String> columnNamesList = Lists.newArrayList(columnNames);
        for (int index = 0; index < columnNamesList.size(); index++) {
            String column = columnNamesList.get(index);
            Matcher m = COLUMN_COUNTER_PATTERN.matcher(column);
            if (m.matches()) {
                columnNamesList.set(index, m.group(1));
            }
        }
        return columnNamesList;
    }

    /**
     * Prints all epics and stories to a file with the given filename.
     * 
     * @param fileName
     *            the name of the file to be generated.
     * @throws FileNotFoundException
     *             if the file could not be created.
     */
    private void printFile(Table<Integer, String, String> data, String fileName)
            throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);

        String columns = Joiner.on(",").join(
                removeCounterFromColumnName(data.columnKeySet()));

        LOGGER.info("Using columns: " + columns);

        // Print all headers in the file.
        pw.println(columns);

        // Print rows.
        for (Integer rowNo = 0; rowNo < data.rowKeySet().size(); rowNo++) {
            List<String> rowContents = new ArrayList<String>();
            Map<String, String> rowData = data.row(rowNo);

            for (String columnName : data.columnKeySet()) {
                String value = rowData.get(columnName);
                rowContents.add(value != null ? value : "");
            }
            pw.println(Joiner.on(",").join(rowContents));
        }

        pw.close();
    }

    /**
     * Adds ampersands around the given value.
     * 
     * @param value
     *            the string value to be surrounded by ampersands.
     * @return the given string surrounded with ampersands.
     */
    private String ampersandify(String value) {
        if (value == null) {
            value = "";
        }
        return "\"" + value + "\"";
    }

    /**
     * 
     * @param book
     * @param sheetConfig
     * @return
     */
    private Sheet getSheet(Workbook book, SheetConfig sheetConfig) {
        String sheetName = sheetConfig.getSheetName();

        int sheetIndex = sheetConfig.getSheetIndex();
        if (sheetIndex == -1) {
            if (Strings.isNullOrEmpty(sheetName)) {
                throw new RuntimeException(
                        "Configuration error. No sheet index or name set.");
            }
            else {
                sheetIndex = book.getSheetIndex(sheetName);
                if (sheetIndex == -1) { // TODO VERIFY THIS!!!
                    throw new RuntimeException(
                            "Configuration error. Sheet with name '"
                                    + sheetName + "' does not exist.");
                }
            }
        }
        return book.getSheetAt(sheetIndex);
    }

    /**
     * Parses the given Excel Workbook and stores the contents in the internal
     * map.
     * 
     * @param book
     *            the Excel Workbook to parse.
     */
    private Table<Integer, String, String> parse(Workbook book,
            List<SheetConfig> sheets) {

        Table<Integer, String, String> data = TreeBasedTable.create();
        int rowCount = 0;

        for (SheetConfig sheetConfig : sheets) {
            Map<RowConfig, Map<FieldConfig, String>> cache = new HashMap<RowConfig, Map<FieldConfig, String>>();

            Sheet sheet = getSheet(book, sheetConfig);

            LOGGER.info("Parsing sheet '" + sheet.getSheetName() + "' using "
                    + sheetConfig);

            for (RowConfig rowConfig : sheetConfig.getRows()) {
                for (int rowNo = sheetConfig.getStartRow(); rowNo < sheet
                        .getPhysicalNumberOfRows(); rowNo++) {
                    Row row = sheet.getRow(rowNo);
                    for (FieldConfig fieldConfig : rowConfig.getFields()) {
                        Splitter fieldSplitter = Splitter
                                .on(CharMatcher.is(fieldConfig
                                        .getMultiValueDelimiter()))
                                .trimResults().omitEmptyStrings();
                        String value = fieldConfig.getValue();
                        if (Strings.isNullOrEmpty(value)) {
                            value = getFieldValue(row, fieldConfig);
                        }

                        // If a row contains a field that must be unique, the
                        // entire row should only be printed if the value is
                        // unique to the entire dataset for the row.

                        // Only print rows that does not contain unique fields.
                        boolean isPrintRow = !fieldConfig.isUnique()
                                || !isValueIncludedAlready(cache, rowConfig,
                                        fieldConfig, value);

                        if (isPrintRow) {
                            if (fieldConfig.isMultiValued()) {
                                int counter = 0;
                                for (String part : fieldSplitter.split(value)) {
                                    String label = enumerateColumnName(
                                            fieldConfig.getLabel(), ++counter);
                                    data.put(rowCount, ampersandify(label),
                                            ampersandify(part));
                                }
                            }
                            else {
                                data.put(rowCount,
                                        ampersandify(fieldConfig.getLabel()),
                                        ampersandify(value));
                            }
                        }
                        else {
                            // Delete all columns for the current row.
                            for (String column : data.columnKeySet()) {
                                data.remove(rowCount, column);
                            }
                            // Break the fieldConfig loop and continue with the
                            // next row.
                            break;
                        }
                    }
                    rowCount++;
                }
            }
        }
        return data;
    }

    /**
     * 
     * @param label
     * @param counter
     * @return
     */
    private String enumerateColumnName(String label, int counter) {
        return label + COLUMN_COUNTER_SEPARATOR + counter;
    }

    /**
     * Checks if <code>value</code> is unique for the given <code>label</code>.
     * 
     * @return true, if the value is unique and already exists, false if the
     *         value was just added.
     */
    private boolean isValueIncludedAlready(
            Map<RowConfig, Map<FieldConfig, String>> cache, RowConfig row,
            FieldConfig field, String value) {
        boolean isUnique = true;

        Map<FieldConfig, String> fieldCache = cache.get(row);
        if (fieldCache == null) {
            isUnique = false;
            fieldCache = new HashMap<FieldConfig, String>();
            cache.put(row, fieldCache);
        }

        if (fieldCache.get(field) == null) {
            isUnique = false;
            fieldCache.put(field, value);
        }
        return isUnique;
    }

    /**
     * 
     * @param row
     * @param config
     * @return
     */
    private String getFieldValue(Row row, FieldConfig config) {

        Cell cell = row.getCell(config.getColumn());
        if (cell == null) {
            if (config.isBreakOnEmpty()) {
                throw new RuntimeException(
                        "Data expectancy error: The cell on row "
                                + (row.getRowNum() + 1) + " and column "
                                + (config.getColumn() + 1)
                                + " is empty. Column name is '"
                                + config.getLabel() + "'");
            }
            else {
                return "";
            }
        }

        // Gets the cell value.
        String value = getCellValue(cell, config.isForceIntegerOnNumberFields());
        if (!Strings.isNullOrEmpty(value)) {

            // Prettify the text if it has been set.
            if (config.isPretty()) {
                value = toTitleCase(value);
            }

            // Append the output prefix if it has been set.
            if (!Strings.isNullOrEmpty(config.getOutputPrefix())) {
                value = config.getOutputPrefix() + value;
            }

            // Remove all whitespace characters (tabs, linebreaks etc).
            value = CharMatcher.JAVA_ISO_CONTROL.removeFrom(value);
        }
        return value;
    }

    /**
     * 
     * @param cell
     * @param isForceIntegerOnNumberFields
     * @return
     */
    private String getCellValue(Cell cell, boolean isForceIntegerOnNumberFields) {
        String value = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                double numericValue = cell.getNumericCellValue();
                if (isForceIntegerOnNumberFields) {
                    value = Integer.toString((int) Math.floor(cell
                            .getNumericCellValue()));
                }
                else {
                    value = String.valueOf(numericValue);
                }
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            default:
                throw new RuntimeException("The cell on row "
                        + cell.getRowIndex() + 1 + " and column "
                        + cell.getColumnIndex()
                        + " contains an unknown cell type: "
                        + cell.getCellType());
        }

        return value == null ? "" : value;
    }

    /**
     * 
     * @param exporter
     */
    protected static void export(ExcelExporter exporter) {
        try {
            Workbook book = exporter.getWorkbook();

            Configurator configurator = exporter.getConfigurator();

            LOGGER.info("Parsing the Excel Workbook using " + configurator);
            List<SheetConfig> sheets = configurator.configure();

            Table<Integer, String, String> data = exporter.parse(book, sheets);

            exporter.printFile(data, exporter.getOutputFileName());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
