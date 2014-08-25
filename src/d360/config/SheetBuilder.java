package d360.config;

import com.google.common.base.Strings;

public class SheetBuilder {

    private SheetConfig s;

    public SheetBuilder() {
        s = new SheetConfig();
    }

    public SheetBuilder setStartRow(int startRow) {
        s.setStartRow(startRow);
        return this;
    }

    public SheetBuilder setIndex(int index) {
        s.setSheetIndex(index);
        return this;
    }

    public SheetBuilder setName(String name) {
        s.setSheetName(name);
        return this;
    }

    /**
     * 
     * @return
     */
    public SheetConfig build() {
        // Validate the sheet: Both index and name not set.
        if (s.getSheetIndex() == -1 && Strings.isNullOrEmpty(s.getSheetName())) {
            throw new RuntimeException(
                    "Sheet index and name cannot both be unset.");
        }

        // Validate the sheet: Both index and name set.
        if (s.getSheetIndex() >= 0 && !Strings.isNullOrEmpty(s.getSheetName())) {
            throw new RuntimeException(
                    "Sheet index and name cannot both be set.");
        }

        return s;
    }
}
