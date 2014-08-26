package d360.config;

import com.google.common.base.Strings;

public class FieldConfig {

    private String label;
    private String value;
    private String outputPrefix;

    private int column;

    private boolean isUnique;
    private boolean isPretty;
    private boolean isBreakOnEmpty;
    private boolean isMultiValued;
    private boolean forceIntegerOnNumberFields;

    private char multiValueDelimiter;

    /**
     * 
     * @param b
     */
    private FieldConfig(Builder b) {
        this.column = b.column;
        this.isUnique = b.isUnique;
        this.isPretty = b.isPretty;
        this.isMultiValued = b.isMultiValued;
        this.multiValueDelimiter = b.multiValueDelimiter;
        this.outputPrefix = b.outputPrefix;
        this.forceIntegerOnNumberFields = b.forceIntegerOnNumberFields;
        this.label = b.label;
        this.value = b.value;
        this.isBreakOnEmpty = b.isBreakOnEmpty;
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
     * 
     * @return
     */
    public boolean isBreakOnEmpty() {
        return isBreakOnEmpty;
    }

    /**
     * @return the isMultiValued
     */
    public boolean isMultiValued() {
        return isMultiValued;
    }

    /**
     * @return the multiValueDelimiter
     */
    public char getMultiValueDelimiter() {
        return multiValueDelimiter;
    }

    /**
     * 
     * @author Marcus Demnert, @marcusdemnert
     * 
     */
    public static class Builder {
        private String label;
        private String value;
        private int column;
        private boolean isUnique;
        private boolean isPretty;
        private boolean isBreakOnEmpty;
        private boolean isMultiValued;
        private char multiValueDelimiter;
        private String outputPrefix;
        private boolean forceIntegerOnNumberFields;

        public Builder(final String label, final int column) {
            this.label = label;
            this.column = column;
            this.isUnique = false;
            this.isPretty = true;
            this.isMultiValued = false;
            this.multiValueDelimiter = 0x00;
            this.outputPrefix = "";
            this.forceIntegerOnNumberFields = false;
            this.label = null;
            this.value = "";
            this.isBreakOnEmpty = true;
        }

        public Builder(final String label, final String value) {
            this(label, -1);
            this.value = value;
        }

        public Builder column(int column) {
            this.column = column;
            return this;
        }

        public Builder forceIntegerOnNumberField() {
            this.forceIntegerOnNumberFields = true;
            return this;
        }

        public Builder outputPrefix(String outputPrefix) {
            this.outputPrefix = outputPrefix;
            return this;
        }

        public Builder breakOnEmpty(boolean isBreakOnEmpty) {
            this.isBreakOnEmpty = isBreakOnEmpty;
            return this;
        }

        public Builder unique() {
            this.isUnique = true;
            return this;
        }

        public Builder multiValued(char multiValueDelimiter) {
            this.multiValueDelimiter = multiValueDelimiter;
            this.isMultiValued = true;
            return this;
        }

        public FieldConfig build() {
            // Ensure all required fields have been set.
            if (Strings.isNullOrEmpty(value) && column < 0) {
                throw new RuntimeException(
                        "Static value missing and no column index set for field '"
                                + label + "'.");
            }

            return new FieldConfig(this);
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
        builder.append("FieldConfig [column=").append(column)
                .append(", isUnique=").append(isUnique).append(", isPretty=")
                .append(isPretty).append(", isBreakOnEmpty=")
                .append(isBreakOnEmpty).append(", isMultiValued=")
                .append(isMultiValued).append(", multiValueDelimiter=")
                .append(multiValueDelimiter).append(", label=").append(label)
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
        result = prime * result + (isMultiValued ? 1231 : 1237);
        result = prime * result + (isPretty ? 1231 : 1237);
        result = prime * result + (isUnique ? 1231 : 1237);
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + multiValueDelimiter;
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
        if (isMultiValued != other.isMultiValued) {
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
        if (multiValueDelimiter != other.multiValueDelimiter) {
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