package DataProvider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import Common.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class BulkAdjustmentData {

    @DataProvider(name = "bulkAdjustmentData")
    public Object[][] bulkAdjustmentData() {

        String excelFile = "src/test/resources/TestData.xlsx";
        List<Object[]> testData = new ArrayList<>();

        // CHỈ ĐỊNH ROW MUỐN CHẠY (THEO SỐ DÒNG EXCEL)
        Set<Integer> rowsToRun = new HashSet<>(Arrays.asList(2,3,4,5,6));
        
        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("BulkAdjustment");
            if (sheet == null) {
                throw new RuntimeException("Không tìm thấy sheet 'BulkAdjustment' trong file Excel!");
            }

            Iterator<Row> rowIterator = sheet.iterator();
            int currentRowNum = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                currentRowNum = row.getRowNum() + 1; // Excel row index (1-based)

                // Bỏ header
//                if (currentRowNum == 1) {
//                    continue;
//                }

                if (currentRowNum == 1 || !rowsToRun.contains(currentRowNum)) {
                    continue;
                }

                if (row == null || ExcelUtils.isRowEmpty(row)) continue;

                String id = ExcelUtils.getCellValue(row.getCell(0));
                String adjustmentType = ExcelUtils.getCellValue(row.getCell(2));
                String points = ExcelUtils.getCellValue(row.getCell(3));
                String justification = ExcelUtils.getCellValue(row.getCell(4));
                String notifyAllAffectedStudents = ExcelUtils.getCellValue(row.getCell(5));
                String expectedMsg = ExcelUtils.getCellValue(row.getCell(6));

                testData.add(new Object[]{
                        id, adjustmentType, points, justification, notifyAllAffectedStudents, expectedMsg
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData.toArray(new Object[0][]);
    }
}
