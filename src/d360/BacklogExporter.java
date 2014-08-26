package d360;

import java.util.ArrayList;
import java.util.List;

import d360.config.Configurator;
import d360.config.FieldConfig;
import d360.config.RowConfig;
import d360.config.SheetConfig;

public class BacklogExporter extends ExcelExporter {

    @Override
    protected String getInputFileName() {
        return "/backlog.xlsx";
    }

    @Override
    protected String getOutputFileName() {
        return "backlog_" + System.currentTimeMillis() + ".csv";
    }

    @Override
    protected Configurator getConfigurator() {
        return new Configurator() {
            public List<SheetConfig> configure() {
                List<SheetConfig> sheets = new ArrayList<SheetConfig>();

                FieldConfig issueTypeField = new FieldConfig.Builder(
                        "Issue Type", "Epic").build();

                FieldConfig deliveryPackageAreaField = new FieldConfig.Builder(
                        "Delivery Period", 0).forceIntegerOnNumberField()
                        .outputPrefix("Period ").build();

                FieldConfig stakeholderField = new FieldConfig.Builder(
                        "Component", 2).multiValued(',').build();

                FieldConfig summaryField = new FieldConfig.Builder("Summary", 3)
                        .build();

                FieldConfig epicNameField = new FieldConfig.Builder(
                        "Epic Name", 3).unique().ignoreRowOnBlank().build();

                FieldConfig functionalAreaField = new FieldConfig.Builder(
                        "Functional Area", 2).build();

                FieldConfig reporterField = new FieldConfig.Builder("Reporter",
                        "marcus.demnert").build();

                RowConfig row = new RowConfig.Builder().fields(issueTypeField,
                        epicNameField, summaryField, stakeholderField,
                        functionalAreaField, deliveryPackageAreaField,
                        reporterField).build();

                // Only one sheet that matters in this file.
                SheetConfig sheet = new SheetConfig.Builder().index(1)
                        .startRow(2).rows(row).build();

                sheets.add(sheet);
                return sheets;
            }
        };
    }

    public static void main(String[] args) {
        export(new BacklogExporter());
    }
}
