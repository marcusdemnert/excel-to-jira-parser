package d360;

import java.util.HashMap;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Strings;

public class FunctionalExporter extends Exporter {

    private final static Logger LOGGER = Logger
            .getLogger(FunctionalExporter.class.getName());

    private static final int FUNCTIONAL_SHEET_NO = 1;
    private static final int START_ROW = 2;
    private static final int FUNCTIONAL_AREA_COL = 2;
    private static final int FUNCTION_COL = 3;
    private static final int DELIVERY_PACKAGE_COL = 0;
    private static final String DELIVERT_PACKAGE_PREFIX = "Delivery Package ";

    /**
     * Constructor. Creates a new <code>Exporter</code> instance.
     */
    public FunctionalExporter() {
        epicMap = new HashMap<String, Epic>();
    }

    /**
     * Parses the given Excel Workbook and stores the contents in the internal
     * map.
     * 
     * @param book
     *            the Excel Workbook to parse.
     */
    private void parse(Workbook book) {

        Sheet sheet = book.getSheetAt(FUNCTIONAL_SHEET_NO);

        for (int rowCount = START_ROW; rowCount < sheet
                .getPhysicalNumberOfRows(); rowCount++) {
            Row row = sheet.getRow(rowCount);

            if (row == null)
                continue;
            try {
                String functionName = getCellValue(row, FUNCTION_COL);
                if (Strings.isNullOrEmpty(functionName)) {
                    throw new RuntimeException("Function on row "
                            + String.valueOf(row.getRowNum() + 1) + " missing");
                }
                Epic epic = new Epic(functionName);
                epicMap.put(functionName, epic);

                // Get the delivery package value.
                String deliveryPackage = getCellValue(row, DELIVERY_PACKAGE_COL);
                if (Strings.isNullOrEmpty(deliveryPackage)) {
                    throw new RuntimeException("Delivery package on row "
                            + String.valueOf(row.getRowNum() + 1) + " missing");
                }
                epic.setDeliveryPackage(DELIVERT_PACKAGE_PREFIX
                        + deliveryPackage);

                // Get the functional area.
                String functionalArea = getCellValue(row, FUNCTIONAL_AREA_COL);
                if (Strings.isNullOrEmpty(deliveryPackage)) {
                    throw new RuntimeException("Functional area on row "
                            + String.valueOf(row.getRowNum() + 1) + " missing");
                }
                epic.setComponent(functionalArea);
                System.out.println( epic);
            }
            catch (IllegalStateException e) {
                LOGGER.severe("Issue found in sheet: " + sheet.getSheetName()
                        + " on row " + String.valueOf(row.getRowNum() + 1));
                throw e;
            }
        }
    }

    /**
     * 
     * @param row
     * @param column
     * @return
     */
    private String getCellValue(Row row, int column) {
        String value = null;
        Cell cell = row.getCell(column);
        String cellValue = getCellValue(cell);
        if (!Strings.isNullOrEmpty(cellValue)) {
            value = toTitleCase(cellValue);
        }
        return value;
    }

    /**
     * Main method. Starts the exporter.
     * 
     * @param args
     *            not used.
     */
    public static void main(String[] args) {
        try {
            Workbook book = new XSSFWorkbook(
                    FunctionalExporter.class
                            .getResourceAsStream("/functional.xlsx"));

            FunctionalExporter exporter = new FunctionalExporter();
            exporter.parse(book);

            exporter.printFile("functions_" + System.currentTimeMillis()
                    + ".csv");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
