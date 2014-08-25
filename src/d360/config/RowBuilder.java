package d360.config;

public class RowBuilder {
    private RowConfig row = new RowConfig();

    public RowBuilder addField(FieldConfig... fields) {
        for (FieldConfig f : fields) {
            row.addField(f);
        }
        return this;
    }

    public RowConfig build() {

        // Ensure all required fields have been set.
        return row;
    }

}