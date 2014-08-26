package d360.config;

import java.util.ArrayList;
import java.util.List;

public abstract class Configurator {

    private List<SheetConfig> sheets;

    private SheetConfig defaultSheet;

    public Configurator() {
        sheets = new ArrayList<SheetConfig>();
        defaultSheet = null;
    }

    public abstract List<SheetConfig> configure();

    /**
     * @return the defaultSheet
     */
    public SheetConfig getDefaultSheet() {
        return defaultSheet;
    }

    /**
     * @param defaultSheet
     *            the defaultSheet to set
     */
    public void setDefaultSheet(SheetConfig defaultSheet) {
        this.defaultSheet = defaultSheet;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Configurator [sheets=").append(sheets)
                .append(", defaultSheet=").append(defaultSheet).append("]");
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
        result = prime * result
                + ((defaultSheet == null) ? 0 : defaultSheet.hashCode());
        result = prime * result + ((sheets == null) ? 0 : sheets.hashCode());
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
        Configurator other = (Configurator) obj;
        if (defaultSheet == null) {
            if (other.defaultSheet != null) {
                return false;
            }
        }
        else if (!defaultSheet.equals(other.defaultSheet)) {
            return false;
        }
        if (sheets == null) {
            if (other.sheets != null) {
                return false;
            }
        }
        else if (!sheets.equals(other.sheets)) {
            return false;
        }
        return true;
    }

}
