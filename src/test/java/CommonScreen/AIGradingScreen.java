package CommonScreen;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import Common.Constant;
import Common.Utilities;

public class AIGradingScreen {
	public static final String FILE_PATH				= "src/test/resources/TestData.xlsx";
	public static final String SHEET_NAME				= "Grade";
	
	// Dropdown
	public static final String ASSIGN_DROPDOWN_XPATH	= "//button[@role='combobox']";
	
	// Button
	public static final String AI_GRADING_BTN_XPATH		= "//button[normalize-space()='Start AI Grading']";
	public static final String RE_GRADE_BTN_XPATH		= "//button[normalize-space()='Re-grade']";
	
	// Message
	public static final String MSG_XPATH				= "//div[@class='text-sm font-semibold']";
		
	public static WebDriver openScreen(String browser) throws Exception {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			LoginScreen.login(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
			Utilities.clickObscuredElement(driver, HomeScreen.MY_SECTIONS_LINK_XPATH, MySectionsScreen.OPEN_SECTION_XPATH2);
			Utilities.clickObscuredElement(driver, MySectionsScreen.OPEN_SECTION_XPATH2, SectionScreen.GRADES_TAB_XPATH);
			Utilities.clickObscuredElement(driver, SectionScreen.GRADES_TAB_XPATH, AIGradingScreen.ASSIGN_DROPDOWN_XPATH);
		}
		return driver;
	}
	
	public static void grade(WebDriver driver, String id, String assignment, String expectedMsg) throws Exception {
        try {
    		Utilities.selectDropdownByVisibleText(driver, ASSIGN_DROPDOWN_XPATH, assignment);
    		if (id.equals("Grade_01")) {
    			Utilities.clickObscuredElement(driver, AI_GRADING_BTN_XPATH, MSG_XPATH);
    		}
    		else {
    			Utilities.clickObscuredElement(driver, RE_GRADE_BTN_XPATH, MSG_XPATH);
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