package DataProvider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import Common.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ReviewAdjustData {

    @DataProvider(name = "reviewAdjustData")
    public Object[][] reviewAdjustData() {

        String excelFile = "src/test/resources/TestData.xlsx";
        List<Object[]> testData = new ArrayList<>();
        
        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("ReviewAdjust");
            if (sheet == null) {
                throw new RuntimeException("Không tìm thấy sheet 'ReviewAdjust' trong file Excel!");
            }

            Iterator<Row> rowIterator = sheet.iterator();
            int currentRowNum = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                currentRowNum = row.getRowNum() + 1; // Excel row index (1-based)

                // Bỏ header
                if (currentRowNum == 1) {
                    continue;
                }

                if (row == null || ExcelUtils.isRowEmpty(row)) continue;

                String id = ExcelUtils.getCellValue(row.getCell(0));
                String newGrade = ExcelUtils.getCellValue(row.getCell(2));
                String adjustmentReason = ExcelUtils.getCellValue(row.getCell(3));
                String customReason = ExcelUtils.getCellValue(row.getCell(4));
                String justification = ExcelUtils.getCellValue(row.getCell(5));
                String notifyStudent = ExcelUtils.getCellValue(row.getCell(6));
                String expectedMsg = ExcelUtils.getCellValue(row.getCell(7));

                testData.add(new Object[]{
                        id, newGrade, adjustmentReason, customReason, justification, notifyStudent, expectedMsg
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData.toArray(new Object[0][]);
    }
}
