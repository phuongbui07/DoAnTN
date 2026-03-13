package CommonScreen;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import Common.Constant;
import Common.Utilities;

public class ReportsScreen {
	public static final String FILE_PATH				= "src/test/resources/TestData.xlsx";
	public static final String SHEET_NAME				= "Reports";
	
	// Dropdown
	public static final String TYPE_DROPDOWN_XPATH		= "(//button[@role='combobox'])[2]";
	public static final String FORMAT_DROPDOWN_XPATH	= "(//button[@role='combobox'])[3]";
		
	// Button
	public static final String FERPA_SW_XPATH			= "(//button[@role='switch'])[1]";
	public static final String MASK_STU_SW_XPATH		= "(//button[@role='switch'])[2]";
	public static final String ANONYMIZE_DATA_SW_XPATH	= "(//button[@role='switch'])[3]";
	public static final String GEN_REPORT_BTN_XPATH		= "//button[normalize-space()='Generate Report']";
	public static final String DOWNLOAD_BTN_XPATH		= "(//button[normalize-space()='Download'])[1]";
	public static final String DELETE_BTN_XPATH			= "(//button)[17]";
	
	// Message
	public static final String MSG_XPATH				= "//div[@class='text-sm font-semibold']";
		
	public static WebDriver openScreen(String browser) throws Exception {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			LoginScreen.login(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
			Utilities.clickObscuredElement(driver, HomeScreen.MY_SECTIONS_LINK_XPATH, MySectionsScreen.OPEN_SECTION_XPATH2);
			Utilities.click(driver, By.xpath(MySectionsScreen.OPEN_SECTION_XPATH));
			Utilities.click(driver, By.xpath(SectionScreen.GRADES_TAB_XPATH));
			Utilities.click(driver, By.xpath(SectionScreen.REPORTS_TAB_XPATH));
		}
		return driver;
	}
	
	public static void reports(WebDriver driver, String id, String reportType, String exportFormat, String FERPACompliant, String maskStudentIDs, String anonymizeData, String expectedMsg) throws Exception {
        try {   		
    		if (id.equals("Reports_15")) {
    			Utilities.clickObscuredElement(driver, DOWNLOAD_BTN_XPATH, MSG_XPATH);
    		}
    		else if (id.equals("Reports_16")) {
    			Utilities.clickObscuredElement(driver, DELETE_BTN_XPATH, MSG_XPATH);
    		}
    		else {
        		Utilities.selectDropdownByVisibleText(driver, TYPE_DROPDOWN_XPATH, reportType);
        		Utilities.selectDropdownByVisibleText(driver, FORMAT_DROPDOWN_XPATH, exportFormat);
        		Utilities.setSwitchStatus(driver, FERPA_SW_XPATH, FERPACompliant);
        		Utilities.setSwitchStatus(driver, MASK_STU_SW_XPATH, maskStudentIDs);
        		Utilities.setSwitchStatus(driver, ANONYMIZE_DATA_SW_XPATH, anonymizeData);
    			Utilities.clickObscuredElement(driver, GEN_REPORT_BTN_XPATH, MSG_XPATH);
    		}
    		Utilities.captureScreen(driver, id);
            // Thực hiện test
            Utilities.assertTextValue(driver, By.xpath(MSG_XPATH), expectedMsg);         
            
            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, SHEET_NAME, id, "PASS");
        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không hiển thị thông báo lỗi \"" + expectedMsg + "\"";
            Utilities.writeTestResult(FILE_PATH, SHEET_NAME, id, "FAIL", actual);
            throw e;
        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);
            String actual = "Hiển thị thông báo lỗi \"" + Utilities.getActualText(e.getMessage()) + "\"";
            Utilities.writeTestResult(FILE_PATH, SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
	}
	
}