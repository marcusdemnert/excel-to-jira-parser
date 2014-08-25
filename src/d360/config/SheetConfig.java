package d360.config;

import java.util.ArrayList;
import java.util.List;

public class SheetConfig {

    private int startRow;
    private int sheetIndex;
    private String sheetName;

    private List<RowConfig> rows;

    protected SheetConfig() {
        rows = new ArrayList<RowConfig>();
        startRow = 0;
        sheetName = null;
        sheetIndex = -1;
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

    /**
     * @param startRow
     *            the startRow to set
     */
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    /**
     * @param sheetIndex
     *            the sheetIndex to set
     */
    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    /**
     * @param sheetName
     *            the sheetName to set
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public void addRow(RowConfig row) {
        rows.add(row);
    }

    public List<RowConfig> getRows() {
        return rows;
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
