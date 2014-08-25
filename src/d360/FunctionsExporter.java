package d360;

import d360.config.Configurator;
import d360.config.FieldBuilder;
import d360.config.FieldConfig;
import d360.config.RowBuilder;
import d360.config.RowConfig;
import d360.config.SheetBuilder;
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
            public void configure() {
                FieldConfig issueTypeField = new FieldBuilder("Issue Type",
                        "Epic").build();

                FieldConfig summaryField = new FieldBuilder("Summary")
                        .setColumn(3).build();

                FieldConfig epicNameField = new FieldBuilder("Epic Name")
                        .setColumn(3).build();

                FieldConfig functionalAreaField = new FieldBuilder(
                        "Functional Area").setColumn(2).build();

                FieldConfig deliveryPackageAreaField = new FieldBuilder(
                        "Delivery Period").setColumn(0)
                        .forceIntegerOnNumberField().setOutputPrefix("Period ")
                        .build();

                FieldConfig reporterField = new FieldBuilder("Reporter",
                        "marcus.demnert").build();

                RowConfig row = new RowBuilder().addField(issueTypeField,
                        epicNameField, summaryField, functionalAreaField,
                        deliveryPackageAreaField, reporterField).build();

                SheetConfig sheet = new SheetBuilder().setIndex(1)
                        .setStartRow(2).build();

                sheet.addRow(row);
                addSheet(sheet);
            }
        };
    }

    public static void main(String[] args) {
        export(new FunctionsExporter());
    }
}
