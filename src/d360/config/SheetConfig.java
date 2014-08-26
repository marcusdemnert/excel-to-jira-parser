package d360.config;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class SheetConfig {

    private int startRow;
    private int sheetIndex;
    private String sheetName;

    private List<RowConfig> rows;

    protected SheetConfig(Builder b) {
        rows = new ArrayList<RowConfig>();
        this.startRow = b.startRow;
        this.sheetName = b.name;
        this.sheetIndex = b.index;
    }

    /**
     * @return the startRow
     */
    public int getStartRow() {
        return startRow;
    }

    /**
     * @return the sheetIndex
     */
    public int getSheetIndex() {
        return sheetIndex;
    }

    /**
     * @return the sheetName
     */
    public String getSheetName() {
        return sheetName;
    }

    public List<RowConfig> getRows() {
        return rows;
    }

    /**
     * 
     * @author Marcus Demnert, @marcusdemnert
     * 
     */
    public static class Builder {

        private int startRow;
        private int index;
        private String name;
        private List<RowConfig> rows;

        public Builder() {
            index = -1;
            startRow = 0;
            name = null;
            rows = new ArrayList<RowConfig>();
        }

        public Builder startRow(int startRow) {
            this.startRow = startRow;
            return this;
        }

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder rows(RowConfig... rows) {
            this.rows.addAll(Lists.newArrayList(rows));
            return this;
        }

        /**
         * 
         * @return
         */
        public SheetConfig build() {
            // Validate the sheet: Both index and name not set.
            if (index == -1 && Strings.isNullOrEmpty(name)) {
                throw new RuntimeException(
                        "Sheet index and name cannot both be unset.");
            }

            // Validate the sheet: Both index and name set.
            if (index >= 0 && !Strings.isNullOrEmpty(name)) {
                throw new RuntimeException(
                        "Sheet index and name cannot both be set.");
            }

            if (rows.isEmpty()) {
                throw new RuntimeException("No rows added to this sheet");
            }

            return new SheetConfig(this);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SheetConfig [startRow=").append(startRow)
                .append(", sheetIndex=").append(sheetIndex)
                .append(", sheetName=").append(sheetName).append(", rows=")
                .append(rows).append("]");
        return builder.toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rows == null) ? 0 : rows.hashCode());
        result = prime * result + sheetIndex;
        result = prime * result
                + ((sheetName == null) ? 0 : sheetName.hashCode());
        result = prime * result + startRow;
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SheetConfig other = (SheetConfig) obj;
        if (rows == null) {
            if (other.rows != null) {
                return false;
            }
        }
        else if (!rows.equals(other.rows)) {
            return false;
        }
        if (sheetIndex != other.sheetIndex) {
            return false;
        }
        if (sheetName == null) {
            if (other.sheetName != null) {
                return false;
            }
        }
        else if (!sheetName.equals(other.sheetName)) {
            return false;
        }
        if (startRow != other.startRow) {
            return false;
        }
        return true;
    }

}
