package d360.config;

public class FieldBuilder {
    private FieldConfig f;

    public FieldBuilder() {
        f = new FieldConfig();
    }

    public FieldBuilder setLabel(String label) {
        f.setLabel(label);
        return this;
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
        return f;
    }

    public FieldBuilder setOutputPrefix(String prefix) {
        f.setOutputPrefix(prefix);
        return this;
    }

    public FieldBuilder setValue(String value) {
        f.setValue(value);
        return this;
    }

    public FieldBuilder isUnique() {
        f.setUnique(true);
        return this;
    }

}
