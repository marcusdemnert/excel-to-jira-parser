package d360;

import java.util.HashMap;
import java.util.Map;

public class SheetConfiguration {

    public int START_ROW;
    public int THEME_COL;
    public int SUMMARY_COL;
    public int DESCRIPTION_COL;

    private final static int DEFAULT_START_ROW = 1;
    private final static int DEFAULT_THEME_COL = 0;
    private final static int DEFAULT_SUMMARY_COL = 2;
    private final static int DEFAULT_DESCRIPTION_COL = 9;

    private static SheetConfiguration DEFAULT = new SheetConfiguration(
            DEFAULT_START_ROW, DEFAULT_THEME_COL, DEFAULT_SUMMARY_COL,
            DEFAULT_DESCRIPTION_COL);

    private static Map<String, SheetConfiguration> sheetConfiguration;

    static {
        sheetConfiguration = new HashMap<String, SheetConfiguration>();

        SheetConfiguration offByOne = new SheetConfiguration(DEFAULT_START_ROW,
                DEFAULT_THEME_COL + 1, DEFAULT_SUMMARY_COL + 1,
                DEFAULT_DESCRIPTION_COL + 1);

        sheetConfiguration.put("Validation", new SheetConfiguration(
                DEFAULT_START_ROW, DEFAULT_THEME_COL, DEFAULT_SUMMARY_COL, 8));
        sheetConfiguration.put("Shopping", new SheetConfiguration(4,
                DEFAULT_THEME_COL + 1, DEFAULT_SUMMARY_COL + 1,
                DEFAULT_DESCRIPTION_COL + 1));
        sheetConfiguration.put("Awareness", offByOne);
        sheetConfiguration.put("Exploration", offByOne);
        sheetConfiguration.put("Uncategorized", offByOne);
    }

    private SheetConfiguration(int startRow, int themeCol, int summaryCol,
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SheetConfiguration [START_ROW=").append(START_ROW)
                .append(", THEME_COL=").append(THEME_COL)
                .append(", SUMMARY_COL=").append(SUMMARY_COL)
                .append(", DESCRIPTION_COL=").append(DESCRIPTION_COL)
                .append("]");
        return builder.toString();
    }

}
