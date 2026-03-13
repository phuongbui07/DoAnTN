package DataProvider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import Common.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class CreateFeedbackData {

    @DataProvider(name = "createFeedbackData")
    public Object[][] createFeedbackData() {

        String excelFile = "src/test/resources/TestData.xlsx";
        List<Object[]> testData = new ArrayList<>();

        // CHỈ ĐỊNH ROW MUỐN CHẠY (THEO SỐ DÒNG EXCEL)
        Set<Integer> rowsToRun = new HashSet<>(Arrays.asList(2,3,4,5,6,7,8,9,10,11,12,13,14));

        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("CreateFeedback");
            if (sheet == null) {
                throw new RuntimeException("Không tìm thấy sheet 'CreateFeedback' trong file Excel!");
            }

            Iterator<Row> rowIterator = sheet.iterator();
            int currentRowNum = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                currentRowNum = row.getRowNum() + 1; // Excel row index (1-based)

                // Bỏ header + row không nằm trong danh sách cần chạy
                if (currentRowNum == 1 || !rowsToRun.contains(currentRowNum)) {
                    continue;
                }

                if (row == null || ExcelUtils.isRowEmpty(row)) continue;

                String id = ExcelUtils.getCellValue(row.getCell(0));
                String level = ExcelUtils.getCellValue(row.getCell(2));
                String comment = ExcelUtils.getCellValue(row.getCell(3));
                String expectedMsg = ExcelUtils.getCellValue(row.getCell(6));

                testData.add(new Object[]{
                        id, level, comment, expectedMsg
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData.toArray(new Object[0][]);
    }
}
