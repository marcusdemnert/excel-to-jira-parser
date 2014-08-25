package d360.config;

public class FieldConfig {

    private int column;
    private boolean isUnique;
    private boolean isPretty;
    private boolean isBreakOnEmpty;

    private String label;
    private String value;

    private String outputPrefix;

    private boolean forceIntegerOnNumberFields;

    protected FieldConfig() {
        column = -1;
        isUnique = false;
        isPretty = true;
        outputPrefix = "";
        forceIntegerOnNumberFields = false;
        label = null;
        value = "";
        isBreakOnEmpty = true;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @return the isUnique
     */
    public boolean isUnique() {
        return isUnique;
    }

    /**
     * @return the isPretty
     */
    public boolean isPretty() {
        return isPretty;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param column
     *            the column to set
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @param isUnique
     *            the isUnique to set
     */
    public void setUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    /**
     * @param isPretty
     *            the isPretty to set
     */
    public void setPretty(boolean isPretty) {
        this.isPretty = isPretty;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the outputPrefix
     */
    public String getOutputPrefix() {
        return outputPrefix;
    }

    /**
     * @return the forceIntegerOnNumberFields
     */
    public boolean isForceIntegerOnNumberFields() {
        return forceIntegerOnNumberFields;
    }

    /**
     * @param outputPrefix
     *            the outputPrefix to set
     */
    public void setOutputPrefix(String outputPrefix) {
        this.outputPrefix = outputPrefix;
    }

    /**
     * @param forceIntegerOnNumberFields
     *            the forceIntegerOnNumberFields to set
     */
    public void setForceIntegerOnNumberFields(boolean forceIntegerOnNumberFields) {
        this.forceIntegerOnNumberFields = forceIntegerOnNumberFields;
    }

    public boolean isBreakOnEmpty() {
        return isBreakOnEmpty;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FieldConfig [column=").append(column)
                .append(", isUnique=").append(isUnique).append(", isPretty=")
                .append(isPretty).append(", isBreakOnEmpty=")
                .append(isBreakOnEmpty).append(", label=").append(label)
                .append(", value=").append(value).append(", outputPrefix=")
                .append(outputPrefix).append(", forceIntegerOnNumberFields=")
                .append(forceIntegerOnNumberFields).append("]");
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
        result = prime * result + column;
        result = prime * result + (forceIntegerOnNumberFields ? 1231 : 1237);
        result = prime * result + (isBreakOnEmpty ? 1231 : 1237);
        result = prime * result + (isPretty ? 1231 : 1237);
        result = prime * result + (isUnique ? 1231 : 1237);
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result
                + ((outputPrefix == null) ? 0 : outputPrefix.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
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
        FieldConfig other = (FieldConfig) obj;
        if (column != other.column) {
            return false;
        }
        if (forceIntegerOnNumberFields != other.forceIntegerOnNumberFields) {
            return false;
        }
        if (isBreakOnEmpty != other.isBreakOnEmpty) {
            return false;
        }
        if (isPretty != other.isPretty) {
            return false;
        }
        if (isUnique != other.isUnique) {
            return false;
        }
        if (label == null) {
            if (other.label != null) {
                return false;
            }
        }
        else if (!label.equals(other.label)) {
            return false;
        }
        if (outputPrefix == null) {
            if (other.outputPrefix != null) {
                return false;
            }
        }
        else if (!outputPrefix.equals(other.outputPrefix)) {
            return false;
        }
        if (value == null) {
            if (other.value != null) {
                return false;
            }
        }
        else if (!value.equals(other.value)) {
            return false;
        }
        return true;
    }

}