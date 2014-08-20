package d360;

import java.util.HashMap;
import java.util.Map;

public class SheetConfiguration {

    final static int DEFAULT_START_ROW = 1;
    final static int DEFAULT_THEME_COL = 0;
    final static int DEFAULT_SUMMARY_COL = 2;
    final static int DEFAULT_DESCRIPTION_COL = 9;

    static SheetConfiguration DEFAULT = new SheetConfiguration(
            DEFAULT_START_ROW, DEFAULT_THEME_COL, DEFAULT_SUMMARY_COL,
            DEFAULT_DESCRIPTION_COL);

    static Map<String, SheetConfiguration> sheetConfiguration = new HashMap<String, SheetConfiguration>();

    static {
        SheetConfiguration offByOne = new SheetConfiguration(DEFAULT_START_ROW,
                DEFAULT_THEME_COL + 1, DEFAULT_SUMMARY_COL + 1,
                DEFAULT_DESCRIPTION_COL + 1);

        sheetConfiguration.put("Validation", new SheetConfiguration(
                DEFAULT_START_ROW, DEFAULT_THEME_COL, DEFAULT_SUMMARY_COL, 8));
        sheetConfiguration.put("Shopping",
                new SheetConfiguration(4, DEFAULT_THEME_COL,
                        DEFAULT_SUMMARY_COL, DEFAULT_DESCRIPTION_COL));
        sheetConfiguration.put("Awareness", offByOne);
        sheetConfiguration.put("Exploration", offByOne);
        sheetConfiguration.put("Uncategorized", offByOne);
    }

    int START_ROW;
    int THEME_COL;
    int SUMMARY_COL;
    int DESCRIPTION_COL;

    public SheetConfiguration(int startRow, int themeCol, int summaryCol,
            int descriptionCol) {
        START_ROW = startRow;
        THEME_COL = themeCol;
        SUMMARY_COL = summaryCol;
        DESCRIPTION_COL = descriptionCol;
    }

    public static SheetConfiguration getSheetConfiguration(String sheetName) {
        if (sheetConfiguration.containsKey(sheetName))
            return sheetConfiguration.get(sheetName);
        return DEFAULT;
    }
}
