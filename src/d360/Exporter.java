package d360;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class Exporter {

    private static final String DEFAULT_REPORTER = "marcus.demnert";

    private static final String[] JIRA_HEADERS = new String[] { "IssueType",
            "Epic Name", "Summary", "Description", "Epic Link", "Reporter" };

    private Map<String, Epic> epicMap;
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
    private String toTitleCase(String value) {
        String[] arr = value.trim().split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equalsIgnoreCase("SAS")) {
                sb.append(arr[i].toUpperCase()).append(" ");
            }
            else {
                try {
                    arr[i].charAt(0);
                }
                catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Given string: '" + value + "'");
                    System.out.println("Arr: " + arr[i]);
                    System.out.println(i);
                }
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
    private void printFile(String fileName) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(fileName);
        pw.println(Joiner.on(",").join(JIRA_HEADERS));

        for (Epic epic : epicMap.values()) {
            String[] epicFields = new String[] { "Epic",
                    ampersandify(epic.getName()), ampersandify(epic.getName()),
                    "", "", DEFAULT_REPORTER };
            pw.println(Joiner.on(",").join(epicFields));

            for (Story story : epic.getStories()) {
                String[] storyFields = new String[] { "Story", "",
                        ampersandify(story.getSummary()),
                        ampersandify(story.getDescription()),
                        ampersandify(epic.getName()), DEFAULT_REPORTER };
                pw.println(Joiner.on(",").join(storyFields));
            }
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

                Epic epic = getEpic(row, sheetConfig);
                Story story = getStory(row, sheetConfig);
                if (story != null) {
                    epic.addStory(story);
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
    private Epic getEpic(Row row, SheetConfiguration sheetConfig) {
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
    private String getCellValue(Cell cell) {
        if (cell != null) {
            String value = cell.getStringCellValue();
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
