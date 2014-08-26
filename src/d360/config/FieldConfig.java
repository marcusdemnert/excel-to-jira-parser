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
    private boolean isForceIntegerOnNumberFields;
    private boolean isIgnoreRowOnBlank;

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
        this.isForceIntegerOnNumberFields = b.forceIntegerOnNumberFields;
        this.label = b.label;
        this.value = b.value;
        this.isBreakOnEmpty = b.isBreakOnEmpty;
        this.isIgnoreRowOnBlank = b.ignoreRowOnBlank;
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
        return isForceIntegerOnNumberFields;
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

    public boolean isIgnoreRowOnBlank() {
        return isIgnoreRowOnBlank;
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
        private boolean ignoreRowOnBlank;
        private boolean isUnique;
        private boolean isPretty;
        private boolean isBreakOnEmpty;
        private boolean isMultiValued;
        private char multiValueDelimiter;
        private String outputPrefix;
        private boolean forceIntegerOnNumberFields;

        /**
         * Creates a new <code>Builder</code>.
         * 
         * @param label
         *            the label of the current field in the output file.
         * @param column
         *            the column index for the field in the input file (zero
         *            based index).
         */
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
            this.ignoreRowOnBlank = false;
        }

        /**
         * Creates a new <code>Builder</code>. The builder automatically prints
         * the given value in the file.
         * 
         * @param label
         *            the label of the current field in the output file.
         * @param value
         *            the static value of the field.
         */
        public Builder(final String label, final String value) {
            this(label, -1);
            this.value = value;
        }

        /**
         * Forces number values to be integers, e.g. removes any decimals. By
         * default, number values are parsed with (at least) one decimal.
         * 
         * @return the current builder.
         */
        public Builder forceIntegerOnNumberField() {
            this.forceIntegerOnNumberFields = true;
            return this;
        }

        /**
         * Prefixes the outputted value with given prefix. If the value is
         * empty, no prefix is added.
         * 
         * @param outputPrefix
         *            the prefix to prepend the value (if the value exists).
         * @return the current builder.
         */
        public Builder outputPrefix(String outputPrefix) {
            this.outputPrefix = outputPrefix;
            return this;
        }

        /**
         * Sets if the exporter should continue even if a field is found empty.
         * If the field is empty, the exporter continues with the next field and
         * leaves the value of this field empty.
         * 
         * @return the current builder.
         */
        public Builder continueIfEmpty() {
            this.isBreakOnEmpty = true;
            return this;
        }

        /**
         * Sets that this field is unique. If the field is unique and the same
         * value has already been found, the entire row is ignored.
         * 
         * @return the current builder.
         */
        public Builder unique() {
            this.isUnique = true;
            return this;
        }

        /**
         * Sets if the field is multi-valued and what the delimiter is. By
         * default, no fields are multi-valued. Please note that all parts are
         * trimmed by default if the fields is multi-valued.
         * 
         * @param multiValueDelimiter
         *            the delimiter char.
         * @return the current builder.
         */
        public Builder multiValued(char multiValueDelimiter) {
            this.multiValueDelimiter = multiValueDelimiter;
            this.isMultiValued = true;
            return this;
        }

        /**
         * Sets if the entire row should be ignored if the current field is
         * blank. Please note that @link {@link #breakOnEmpty(boolean)} is
         * checked first.
         * 
         * @return the current builder.
         */
        public Builder ignoreRowOnBlank() {
            this.ignoreRowOnBlank = true;
            return this;
        }

        /**
         * Builds the <code>FieldConfig</code> object from the current builder.
         * 
         * @return a configured <code>FieldConfig</code> object.
         */
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
        StringBuilder builder2 = new StringBuilder();
        builder2.append("FieldConfig [label=").append(label).append(", value=")
                .append(value).append(", outputPrefix=").append(outputPrefix)
                .append(", column=").append(column).append(", isUnique=")
                .append(isUnique).append(", isPretty=").append(isPretty)
                .append(", isBreakOnEmpty=").append(isBreakOnEmpty)
                .append(", isMultiValued=").append(isMultiValued)
                .append(", isForceIntegerOnNumberFields=")
                .append(isForceIntegerOnNumberFields)
                .append(", isIgnoreRowOnBlank=").append(isIgnoreRowOnBlank)
                .append(", multiValueDelimiter=").append(multiValueDelimiter)
                .append("]");
        return builder2.toString();
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
        result = prime * result + (isBreakOnEmpty ? 1231 : 1237);
        result = prime * result + (isForceIntegerOnNumberFields ? 1231 : 1237);
        result = prime * result + (isIgnoreRowOnBlank ? 1231 : 1237);
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
        if (isBreakOnEmpty != other.isBreakOnEmpty) {
            return false;
        }
        if (isForceIntegerOnNumberFields != other.isForceIntegerOnNumberFields) {
            return false;
        }
        if (isIgnoreRowOnBlank != other.isIgnoreRowOnBlank) {
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