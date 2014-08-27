package d360;

import java.util.ArrayList;
import java.util.List;

import d360.config.Configurator;
import d360.config.FieldConfig;
import d360.config.RowConfig;
import d360.config.SheetConfig;

public class FunctionsExporter extends ExcelExporter {

    @Override
    protected String getInputFileName() {
        return "/functional.xlsx";
    }

    @Override
    protected String getOutputFileName() {
        return "functional_" + System.currentTimeMillis() + ".csv";
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
                        .outputPrefix("Delivery Period ").build();

                FieldConfig functionalAreaField = new FieldConfig.Builder(
                        "Functional Area", 2).build();

                FieldConfig summaryField = new FieldConfig.Builder("Summary", 3)
                        .build();

                FieldConfig epicNameField = new FieldConfig.Builder(
                        "Epic Name", 3).build();

                FieldConfig reporterField = new FieldConfig.Builder("Reporter",
                        "marcus.demnert").build();

                RowConfig row = new RowConfig.Builder().fields(issueTypeField,
                        epicNameField, summaryField, functionalAreaField,
                        deliveryPackageAreaField, reporterField).build();

                SheetConfig sheet = new SheetConfig.Builder().index(1)
                        .startRow(2).rows(row).build();

                sheets.add(sheet);
                return sheets;
            }
        };
    }

    public static void main(String[] args) {
        export(new FunctionsExporter());
    }
}
