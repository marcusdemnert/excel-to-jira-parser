package d360;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class Exporter {

    private final static Logger LOGGER = Logger.getLogger(Exporter.class
            .getName());

    private static final String DEFAULT_REPORTER = "marcus.demnert";

    private static final String[] JIRA_HEADERS = new String[] {
            "IssueType", "Epic Name", "Summary", "Description", "Epic Link",
            "Functional Area", "Delivery Package", "Reporter"
    };

    protected Map<String, Epic> epicMap;
    private Epic NO_STORY_EPIC = new Epic("No Theme Area");

    /**
     * Constructor. Creates a new <code>Exporter</code> instance.
     */
    public Exporter() {
        epicMap = new HashMap<String, Epic>();
        epicMap.put("No Epic", NO_STORY_EPIC);
    }

    /**
     * Prettifies the given string be making everything lower case and then
     * upper case the first character in each sentence.
     * 
     * @param value
     *            the value to prettify.
     * @return the prettified value.
     */
    protected String toTitleCase(String value) {
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
     * Prints all epics and stories to a file with the given filename.
     * 
     * @param fileName
     *            the name of the file to be generated.
     * @throws FileNotFoundException
     *             if the file could not be created.
     */
    protected void printFile(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println(Joiner.on(",").join(JIRA_HEADERS));

        for (Epic epic : epicMap.values()) {
            printEpic(epic, pw);

            for (Story story : epic.getStories()) {
                printStory(story, epic, pw);
            }
        }

        pw.close();
    }

    /**
     * 
     * @param epic
     * @param pw
     */
    protected void printEpic(Epic epic, PrintWriter pw) {
        String[] epicFields = new String[] {
                "Epic", // Issue Type
                ampersandify(epic.getName()), // Epic Name
                ampersandify(epic.getName()), // Summary
                "", // Description
                "", // Epic Link
                ampersandify(epic.getComponent()), // Functional Area
                ampersandify(epic.getDeliveryPackage()), // Delivery Package
                DEFAULT_REPORTER
        };
        pw.println(Joiner.on(",").join(epicFields));
    }

    /**
     * 
     * @param story
     * @param epic
     * @param pw
     */
    protected void printStory(Story story, Epic epic, PrintWriter pw) {
        String[] storyFields = new String[] {
                "Story", // Issue Type
                "", // Epic Name
                ampersandify(story.getSummary()), // Summary
                ampersandify(story.getDescription()), // Description
                ampersandify(epic.getName()), // Epic Link
                (story.getComponent() != null ? ampersandify(story
                        .getComponent().getName()) : ""), // Functional
                                                          // Area
                "", // Delivery Package
                DEFAULT_REPORTER
        };
        pw.println(Joiner.on(",").join(storyFields));

    }

    /**
     * Adds ampersands around the given value.
     * 
     * @param value
     *            the string value to be surrounded by ampersands.
     * @return the given string surrounded with ampersands.
     */
    protected String ampersandify(String value) {
        if (value == null) {
            value = "";
        }
        return "\"" + value + "\"";
    }

    /**
     * Parses the given Excel Workbook and stores the contents in the internal
     * map.
     * 
     * @param book
     *            the Excel Workbook to parse.
     */
    private void parseExcel(Workbook book) {

        for (int sheetCount = 0; sheetCount < book.getNumberOfSheets(); sheetCount++) {
            Sheet sheet = book.getSheetAt(sheetCount);
            SheetConfiguration sheetConfig = SheetConfiguration
                    .getSheetConfiguration(sheet.getSheetName());

            for (int rowCount = sheetConfig.START_ROW; rowCount < sheet
                    .getPhysicalNumberOfRows(); rowCount++) {
                Row row = sheet.getRow(rowCount);

                if (row == null)
                    continue;
                try {
                    Epic epic = getEpic(row, sheetConfig);
                    Story story = getStory(row, sheetConfig);
                    if (story != null) {
                        epic.addStory(story);
                    }
                }
                catch (IllegalStateException e) {
                    LOGGER.severe("Issue found in sheet: "
                            + sheet.getSheetName() + " on row "
                            + String.valueOf(row.getRowNum() + 1)
                            + ", using sheet configuration " + sheetConfig);
                    throw e;
                }
            }
        }
    }

    /**
     * Gets the epic for the given row.
     * 
     * @param row
     *            the row to get the epic from.
     * @param sheetConfig
     *            the sheet configuration to use.
     * @return the parsed epic or the default epic if no epic was found.
     */
    private Epic getEpic(Row row, SheetConfiguration sheetConfig)
            throws IllegalStateException {
        Cell cell = row.getCell(sheetConfig.THEME_COL);
        String value = getCellValue(cell);
        if (!Strings.isNullOrEmpty(value)) {
            String pretty = toTitleCase(value);
            Epic epic = epicMap.get(pretty);
            if (epic == null) {
                epic = new Epic(pretty);
                epicMap.put(pretty, epic);
            }
            return epic;
        }
        return NO_STORY_EPIC;
    }

    /**
     * Gets the story for the given row.
     * 
     * @param row
     *            the row to get the story from.
     * @param sheetConfig
     *            the sheet configuration to use.
     * @return the parsed story or the default epic if no epic was found.
     */
    private Story getStory(Row row, SheetConfiguration sheetConfig) {
        Story story = null;
        String summary = getCellValue(row.getCell(sheetConfig.SUMMARY_COL));
        String description = getCellValue(row
                .getCell(sheetConfig.DESCRIPTION_COL));
        if (!Strings.isNullOrEmpty(summary)) {
            story = new Story(summary, description);
        }
        return story;
    }

    /**
     * Gets the value of a cell. If the cell does not contain a string value, a
     * RuntimeException will be thrown.
     * 
     * @param cell
     *            the Excel cell to get the value from.
     * @return the string value of the cell or null if empty.
     */
    protected String getCellValue(Cell cell) {
        return getCellValue(cell, true);
    }

    protected String getCellValue(Cell cell, boolean forceIntegerIfNumberic)
            throws IllegalStateException {
        if (cell != null) {
            String value = "";

            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    double numericValue = cell.getNumericCellValue();
                    if (forceIntegerIfNumberic) {
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
                    value = null;
                    break;
                default:
                    throw new IllegalStateException(
                            "Cell value of unknown type: " + cell.getCellType());
            }
            if (!Strings.isNullOrEmpty(value)) {
                return value;
            }
        }
        return null;
    }

    /**
     * Main method. Starts the exporter.
     * 
     * @param args
     *            not used.
     */
    public static void main(String[] args) {
        try {
            Workbook book = new XSSFWorkbook(
                    Exporter.class.getResourceAsStream("/backlog.xlsx"));

            Exporter exporter = new Exporter();
            exporter.parseExcel(book);

            exporter.printFile("businessbacklog" + System.currentTimeMillis()
                    + ".csv");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
