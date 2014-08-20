package d360;

import java.util.HashMap;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Strings;

public class FunctionalExporter extends Exporter {

    private final static Logger LOGGER = Logger
            .getLogger(FunctionalExporter.class.getName());

    private static final int FUNCTIONAL_SHEET_NO = 1;
    private static final int START_ROW = 2;
    private static final int FUNCTIONAL_AREA_COL = 2;
    private static final int FUNCTION_COL = 3;

    private Epic EMPTY_EPIC = new Epic("No Functional Area");

    /**
     * Constructor. Creates a new <code>Exporter</code> instance.
     */
    public FunctionalExporter() {
        epicMap = new HashMap<String, Epic>();
        epicMap.put("Empty Epic", EMPTY_EPIC);
    }

    /**
     * Parses the given Excel Workbook and stores the contents in the internal
     * map.
     * 
     * @param book
     *            the Excel Workbook to parse.
     */
    private void parse(Workbook book) {

        Sheet sheet = book.getSheetAt(FUNCTIONAL_SHEET_NO);

        for (int rowCount = START_ROW; rowCount < sheet
                .getPhysicalNumberOfRows(); rowCount++) {
            Row row = sheet.getRow(rowCount);

            if (row == null)
                continue;
            try {
                Epic epic = getEpic(row);
                Story story = getStory(row);
                if (story != null) {
                    epic.addStory(story);
                }
            }
            catch (IllegalStateException e) {
                LOGGER.severe("Issue found in sheet: " + sheet.getSheetName()
                        + " on row " + String.valueOf(row.getRowNum() + 1));
                throw e;
            }
        }
    }

    /**
     * Gets the epic for the given row.
     * 
     * @param row
     *            the row to get the epic from.
     * @return the parsed epic or the default epic if no epic was found.
     */
    private Epic getEpic(Row row) throws IllegalStateException {
        Cell cell = row.getCell(FUNCTIONAL_AREA_COL);
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
        return EMPTY_EPIC;
    }

    /**
     * Gets the story for the given row.
     * 
     * @param row
     *            the row to get the story from.
     * @return the parsed story or the default epic if no epic was found.
     */
    private Story getStory(Row row) {
        Story story = null;
        String summary = getCellValue(row.getCell(FUNCTION_COL));
        if (!Strings.isNullOrEmpty(summary)) {
            story = new Story(summary, null);
        }
        return story;
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
                    FunctionalExporter.class
                            .getResourceAsStream("/functional.xlsx"));

            FunctionalExporter exporter = new FunctionalExporter();
            exporter.parse(book);

            exporter.printFile("functions_" + System.currentTimeMillis()
                    + ".csv");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
