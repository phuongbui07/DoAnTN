package DataProvider;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

import Common.ExcelUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class MaterialData {

    @DataProvider(name = "materialData")
    public Object[][] materialData() {

        String excelFile = "src/test/resources/TestData.xlsx";
        List<Object[]> testData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("EditMaterials");
            if (sheet == null) {
                throw new RuntimeException("Không tìm thấy sheet 'EditMaterials' trong file Excel!");
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
                String status = ExcelUtils.getCellValue(row.getCell(2));
                String type = ExcelUtils.getCellValue(row.getCell(3));
                String visibility = ExcelUtils.getCellValue(row.getCell(4));
                String folder = ExcelUtils.getCellValue(row.getCell(5));
                String addTag = ExcelUtils.getCellValue(row.getCell(6));
                String AllowDownloads = ExcelUtils.getCellValue(row.getCell(7));
                String AllowPreviews = ExcelUtils.getCellValue(row.getCell(8));
                String AvailableFrom = ExcelUtils.getCellValue(row.getCell(9));
                String AvailableUntil = ExcelUtils.getCellValue(row.getCell(10));
                String Note = ExcelUtils.getCellValue(row.getCell(11));
                String Notify = ExcelUtils.getCellValue(row.getCell(12));
                String expectedMsg = ExcelUtils.getCellValue(row.getCell(13));

                testData.add(new Object[]{
                        id, status, type, visibility, folder, addTag, AllowDownloads, AllowPreviews, AvailableFrom, AvailableUntil, Note, Notify, expectedMsg
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testData.toArray(new Object[0][]);
    }
}
