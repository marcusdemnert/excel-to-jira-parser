package d360;

import java.io.FileNotFoundException;
import java.io.IOException;
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

    SheetConfiguration sheetConfig;

    Map<String, Epic> epicMap;
    private Epic NO_STORY_EPIC = new Epic("No Theme Area");

    public Exporter() {
        epicMap = new HashMap<String, Epic>();
        epicMap.put("No Epic", NO_STORY_EPIC);
    }

    public static void main(String[] args) throws FileNotFoundException,
            IOException {
        Workbook book = new XSSFWorkbook(
                Exporter.class.getResourceAsStream("/backlog.xlsx"));

        Exporter exporter = new Exporter();
        exporter.parseExcel(book);

        exporter.printFile("businessbacklog" + System.currentTimeMillis()
                + ".csv");
    }

    private String toTitleCase(String givenString) {
        String[] arr = givenString.trim().split(" ");
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
                    System.out.println("Given string: '" + givenString + "'");
                    System.out.println("Arr: " + arr[i]);
                    System.out.println(i);
                }
                sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append(" ");
            }
        }
        return sb.toString().trim();
    }

    private void printFile(String fileName) throws FileNotFoundException {
        // IssueType, Summary, FixVersion, FixVersion, FixVersion, Component,
        // Component, Issue ID, Parent ID, Reporter
        PrintWriter pw = new PrintWriter(fileName);
        pw.println("IssueType, Epic Name, Summary, Description, Epic Link, Reporter");

        for (Epic epic : epicMap.values()) {
            String[] epicFields = new String[] { "Epic",
                    ampersandify(epic.name), ampersandify(epic.name), "", "",
                    DEFAULT_REPORTER };
            pw.println(Joiner.on(",").join(epicFields));

            for (Story story : epic.stories) {
                String[] storyFields = new String[] { "Story", "",
                        ampersandify(story.summary),
                        ampersandify(story.description),
                        ampersandify(epic.name), DEFAULT_REPORTER };
                pw.println(Joiner.on(",").join(storyFields));
            }
        }

        pw.close();

    }

    private String ampersandify(String value) {
        return "\"" + value + "\"";
    }

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
                epic.addStory(story);
            }
        }
    }

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

    private Story getStory(Row row, SheetConfiguration sheetConfig) {
        Story story = new Story(
                getCellValue(row.getCell(sheetConfig.SUMMARY_COL)),
                getCellValue(row.getCell(sheetConfig.DESCRIPTION_COL)));
        return story;
    }

    private String getCellValue(Cell cell) {
        if (cell != null) {
            String value = cell.getStringCellValue();
            if (!Strings.isNullOrEmpty(value)) {
                return value;
            }
        }
        return null;
    }

}
