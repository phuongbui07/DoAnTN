package CommonScreen;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import Common.Constant;
import Common.Utilities;

public class ReviewAndAdjustScreen {
	public static final String FILE_PATH				= "src/test/resources/TestData.xlsx";
	public static final String RA_SHEET_NAME			= "ReviewAdjust";
	public static final String BA_SHEET_NAME			= "BulkAdjustment";
	
	// Tab
	public static final String BULK_ADJ_TAB_XPATH		= "(//button[normalize-space()='Bulk Adjustment'])[1]";
	
	// Text field
	public static final String NEW_GRADE_TXT_XPATH		= "//input[@placeholder='Enter new grade (0-100)']";
	public static final String JUSTIFICATION_TXT_XPATH	= "//textarea[@placeholder='Explain the reason for this adjustment (minimum 10 characters)...']";
	public static final String CUS_REASON_TXT_XPATH		= "//input[@placeholder='Enter custom reason...']";
	public static final String POINTS_TXT_XPATH			= "//input[contains(@placeholder,'Enter points to add')]";
	public static final String JUSTIFICATION_TXT_XPATH1 = "//textarea[@placeholder='Explain the reason for this bulk adjustment...']";
	
	// Other
	public static final String DROPDOWN_XPATH			= "(//button[@role='combobox'])[2]";
	public static final String NOTIFY_SW_XPATH			= "//button[@role='switch']";
	
	// Button
	public static final String SCORE_BTN_XPATH			= "(//div[@class='space-y-2 max-h-96 overflow-y-auto']//div//div//div[2]//span)[1]";
	public static final String APPLY_BTN_XPATH			= "//button[contains(normalize-space(.),'Apply')]";
	
	// Message
	public static final String MSG_XPATH				= "//div[@class='text-sm font-semibold']";
	public static final String VALIDATE_MSG_XPATH		= "(//body//div[3]/p)[1]";
	
	public static WebDriver openReviewAdjustScreen(String browser) throws Exception {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			LoginScreen.login(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
			Utilities.clickObscuredElement(driver, HomeScreen.MY_SECTIONS_LINK_XPATH, MySectionsScreen.OPEN_SECTION_XPATH2);
			Utilities.clickObscuredElement(driver, MySectionsScreen.OPEN_SECTION_XPATH, SectionScreen.GRADES_TAB_XPATH);
			Utilities.clickObscuredElement(driver, SectionScreen.GRADES_TAB_XPATH, SectionScreen.REVIEW_TAB_XPATH);
			Utilities.click(driver, By.xpath(SectionScreen.REVIEW_TAB_XPATH));
			Utilities.clickObscuredElement(driver, SCORE_BTN_XPATH, APPLY_BTN_XPATH);
		}
		return driver;
	}
	
	public static WebDriver openBulkAdjustmentScreen(String browser) throws Exception {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			LoginScreen.login(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
			Utilities.clickObscuredElement(driver, HomeScreen.MY_SECTIONS_LINK_XPATH, MySectionsScreen.OPEN_SECTION_XPATH2);
			Utilities.click(driver, By.xpath(MySectionsScreen.OPEN_SECTION_XPATH));
			Utilities.click(driver, By.xpath(SectionScreen.GRADES_TAB_XPATH));
			Utilities.click(driver, By.xpath(SectionScreen.REVIEW_TAB_XPATH));
			Utilities.click(driver, By.xpath(BULK_ADJ_TAB_XPATH));
		}
		return driver;
	}
	
	public static void reviewAdjust(WebDriver driver, String id, String newGrade, String adjustmentReason, String customReason, String justification, String notifyStudent, String expectedMsg) throws Exception {
        try {
        	Utilities.inputValueAndValidate(driver, By.xpath(NEW_GRADE_TXT_XPATH), newGrade, newGrade.replace("+", ""));
    		Utilities.selectDropdownByVisibleText(driver, DROPDOWN_XPATH, adjustmentReason);
    		if (adjustmentReason.equals("Custom")) {
    			Utilities.inputValueAndValidate(driver, By.xpath(CUS_REASON_TXT_XPATH), customReason, customReason.length() <= 255 ? customReason : customReason.substring(0, 255));
    		}
    		Utilities.inputValueAndValidate(driver, By.xpath(JUSTIFICATION_TXT_XPATH), justification, justification.length() <= 2000 ? justification : justification.substring(0, 2000));
    		Utilities.setSwitchStatus(driver, NOTIFY_SW_XPATH, notifyStudent);
    		Utilities.clickObscuredElement(driver, APPLY_BTN_XPATH, MSG_XPATH);
    		Utilities.captureScreen(driver, id);
            // Thực hiện test
            Utilities.assertTextValue(driver, By.xpath(MSG_XPATH), expectedMsg);         
            
            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, RA_SHEET_NAME, id, "PASS");
        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không hiển thị thông báo lỗi \"" + expectedMsg + "\"";
            Utilities.writeTestResult(FILE_PATH, RA_SHEET_NAME, id, "FAIL", actual);
            throw e;
        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);
            String actual = "Hiển thị thông báo lỗi \"" + Utilities.getActualText(e.getMessage()) + "\"";
            Utilities.writeTestResult(FILE_PATH, RA_SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
	}
	
	public static void bulkAdjustment(WebDriver driver, String id, String adjustmentType, String points, String justification, String notifyAllAffectedStudents, String expectedMsg) throws Exception {
        try {
        	String msgXpath;
        	Utilities.selectDropdownByVisibleText(driver, DROPDOWN_XPATH, adjustmentType);
        	Utilities.inputValueAndValidate(driver, By.xpath(POINTS_TXT_XPATH), points, points.replace("+", ""));
    		Utilities.inputValueAndValidate(driver, By.xpath(JUSTIFICATION_TXT_XPATH1), justification, justification.length() <= 2000 ? justification : justification.substring(0, 2000));
    		Utilities.setSwitchStatus(driver, NOTIFY_SW_XPATH, notifyAllAffectedStudents);
    		if (expectedMsg.equals("Grades Adjusted") || expectedMsg.contains("Error")) {
    			msgXpath = MSG_XPATH;
    		}
    		else {
    			msgXpath = VALIDATE_MSG_XPATH;
    		}
    		Utilities.click(driver, By.xpath(APPLY_BTN_XPATH));
//    		Utilities.clickObscuredElement(driver, APPLY_BTN_XPATH, msgXpath);
    		Utilities.captureScreen(driver, id);
            // Thực hiện test
            Utilities.assertTextValue(driver, By.xpath(MSG_XPATH), expectedMsg);         
            
            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, BA_SHEET_NAME, id, "PASS");
        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không hiển thị thông báo lỗi \"" + expectedMsg + "\"";
            Utilities.writeTestResult(FILE_PATH, BA_SHEET_NAME, id, "FAIL", actual);
            throw e;
        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);
            String actual = "Hiển thị thông báo lỗi \"" + Utilities.getActualText(e.getMessage()) + "\"";
            Utilities.writeTestResult(FILE_PATH, BA_SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
	}
}