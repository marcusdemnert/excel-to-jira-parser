package d360.config;

import com.google.common.base.Strings;

public class FieldBuilder {
    private FieldConfig f;

    public FieldBuilder(final String label) {
        f = new FieldConfig();
        f.setLabel(label);
    }

    public FieldBuilder(final String label, final String value) {
        f = new FieldConfig();
        f.setLabel(label);
        f.setValue(value);
    }

    public FieldBuilder setColumn(int column) {
        f.setColumn(column);
        return this;
    }

    public FieldBuilder forceIntegerOnNumberField() {
        f.setForceIntegerOnNumberFields(true);
        return this;
    }

    public FieldConfig build() {
        // Ensure all required fields have been set.
        if (Strings.isNullOrEmpty(f.getValue()) && f.getColumn() < 0) {
            throw new RuntimeException(
                    "Static value missing and no column index set for field '"
                            + f.getLabel() + "'.");
        }

        return f;
    }

    public FieldBuilder setOutputPrefix(String prefix) {
        f.setOutputPrefix(prefix);
        return this;
    }

    public FieldBuilder isUnique() {
        f.setUnique(true);
        return this;
    }

    public FieldBuilder setMultiValued(char delimiter) {
        f.setMultiValued(true, delimiter);
        return this;
    }

}
