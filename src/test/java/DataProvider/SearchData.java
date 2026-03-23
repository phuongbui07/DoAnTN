package DataProvider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import Common.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class SearchData {

    @DataProvider(name = "searchData")
    public Object[][] searchData() {

        String excelFile = "src/test/resources/TestData.xlsx";
        List<Object[]> testData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Search");
            if (sheet == null) {
                throw new RuntimeException("Không tìm thấy sheet 'Search' trong file Excel!");
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
                String search = ExcelUtils.getCellValue(row.getCell(2));
                String status = ExcelUtils.getCellValue(row.getCell(3));
                String showDeleted = ExcelUtils.getCellValue(row.getCell(4));
                String type = ExcelUtils.getCellValue(row.getCell(5));
                String languages = ExcelUtils.getCellValue(row.getCell(6));
                String deadline = ExcelUtils.getCellValue(row.getCell(7));
                String clearFilter = ExcelUtils.getCellValue(row.getCell(8));
                String export = ExcelUtils.getCellValue(row.getCell(9));
                String expectedMsg = ExcelUtils.getCellValue(row.getCell(10));

                testData.add(new Object[]{
                        id, search, status, showDeleted, type, languages, deadline, clearFilter, export, expectedMsg
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData.toArray(new Object[0][]);
    }
}
