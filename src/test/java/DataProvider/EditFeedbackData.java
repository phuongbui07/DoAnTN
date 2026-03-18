package DataProvider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import Common.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class EditFeedbackData {

    @DataProvider(name = "editFeedbackData")
    public Object[][] editFeedbackData() {

        String excelFile = "src/test/resources/TestData.xlsx";
        List<Object[]> testData = new ArrayList<>();

        // CHỈ ĐỊNH ROW MUỐN CHẠY (THEO SỐ DÒNG EXCEL)
        Set<Integer> rowsToRun = new HashSet<>(Arrays.asList(15,16,17,18,19,20,21,22,23,24,25,26,27));

        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("UpdateFeedback");
            if (sheet == null) {
                throw new RuntimeException("Không tìm thấy sheet 'UpdateFeedback' trong file Excel!");
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
                String feedbackType = ExcelUtils.getCellValue(row.getCell(4));
                String yourFeedback = ExcelUtils.getCellValue(row.getCell(5));
                String expectedMsg = ExcelUtils.getCellValue(row.getCell(6));

                testData.add(new Object[]{
                        id, feedbackType, yourFeedback, expectedMsg
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData.toArray(new Object[0][]);
    }
}
