package CommonScreen;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Common.Constant;
import Common.Utilities;

public class FeedbackScreen {
	public static final String FILE_PATH				= "src/test/resources/TestData.xlsx";
	public static final String CREATE_SHEET_NAME		= "CreateFeedback";
	public static final String UPDATE_SHEET_NAME		= "UpdateFeedback";
	public static final String DELETE_SHEET_NAME		= "DeleteFeedback";
	
	// Tab
	public static final String DRAFTS_TAB_XPATH			= "//button[contains(normalize-space(.),'Drafts')]";
		
	// Button
	public static final String EDIT_BTN_XPATH			= "(//div[@class='flex items-start justify-between'])[1]//div[2]//button[2]";
	public static final String DELETE_BTN_XPATH			= "(//div[@class='flex items-start justify-between'])[1]//div[2]//button[4]";
	public static final String PUBLISH_BTN_XPATH		= "//button[normalize-space()='Create & Publish']";
	public static final String SAVE_BTN_XPATH			= "//button[normalize-space()='Save as Draft']";
	public static final String ADD_BTN_XPATH			= "//button[normalize-space()='Add']";
	public static final String INFO_BTN_XAPTH			= "//button[@title='Info']";
	public static final String WARNING_BTN_XAPTH		= "//button[@title='Warning']";
	public static final String ERROR_BTN_XAPTH			= "//button[@title='Error']";
	public static final String SUGGESTION_BTN_XAPTH		= "//button[@title='Suggestion']";
	public static final String LINE_BTN_XPATH			= "//div[@class='line-numbers active-line-number']";
	public static final String SAVE_CHANGES_BTN_XPATH	= "//button[normalize-space()='Save Changes']";
	public static final String CANCEL_BTN_XPATH			= "//button[normalize-space()='Cancel']";
	
	// Combobox
	public static final String DROPDOWN_XPATH			= "(//button[@role='combobox'])[2]";
	public static final String YOUR_FEEDBACK_TXT_XPATH	= "//textarea[@placeholder='Write personalized feedback for the student. You can reference the code and AI analysis in the middle column...']";
	public static final String YOUR_FEEDBACK_TXT_XPATH2	= "//textarea[@placeholder='Write feedback content...']";
	public static final String COMMENT_TXT_XPATH		= "//textarea[@placeholder='Enter your comment...']";
	
	// Message
	public static final String MSG_XPATH				= "//div[@class='text-sm font-semibold']";
		
	public static WebDriver openFeedbackScreen(String browser) throws Exception {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			LoginScreen.login(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
			Utilities.clickObscuredElement(driver, HomeScreen.MY_SECTIONS_LINK_XPATH, MySectionsScreen.OPEN_SECTION_XPATH2);
			Utilities.click(driver, By.xpath(MySectionsScreen.OPEN_SECTION_XPATH));
			Utilities.click(driver, By.xpath(SectionScreen.GRADES_TAB_XPATH));
			Utilities.click(driver, By.xpath(SectionScreen.FEEDBACK_TAB_XPATH));
		}
		return driver;
	}
	
	public static WebDriver openDrafsScreen(String browser) throws Exception {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			LoginScreen.login(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
			Utilities.clickObscuredElement(driver, HomeScreen.MY_SECTIONS_LINK_XPATH, MySectionsScreen.OPEN_SECTION_XPATH2);
			Utilities.click(driver, By.xpath(MySectionsScreen.OPEN_SECTION_XPATH));
			Utilities.click(driver, By.xpath(SectionScreen.GRADES_TAB_XPATH));
			Utilities.click(driver, By.xpath(SectionScreen.FEEDBACK_TAB_XPATH));
			Utilities.click(driver, By.xpath(DRAFTS_TAB_XPATH));
		}
		return driver;
	}
	
	public static WebDriver openEditScreen(String browser) throws Exception {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			LoginScreen.login(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
			Utilities.clickObscuredElement(driver, HomeScreen.MY_SECTIONS_LINK_XPATH, MySectionsScreen.OPEN_SECTION_XPATH2);
			Utilities.click(driver, By.xpath(MySectionsScreen.OPEN_SECTION_XPATH));
			Utilities.click(driver, By.xpath(SectionScreen.GRADES_TAB_XPATH));
			Utilities.click(driver, By.xpath(SectionScreen.FEEDBACK_TAB_XPATH));
			Utilities.click(driver, By.xpath(DRAFTS_TAB_XPATH));
			Utilities.click(driver, By.xpath(EDIT_BTN_XPATH));
		}
		return driver;
	}
	
	public static void create(WebDriver driver, String id, String level, String comment, String expectedMsg) throws Exception {
        try {  
        	Utilities.click(driver, By.xpath(LINE_BTN_XPATH));
        	switch (level) {
			case "Info": {
				Utilities.click(driver, By.xpath(INFO_BTN_XAPTH));
				break;
			}
			case "Warning": {
				Utilities.click(driver, By.xpath(WARNING_BTN_XAPTH));
				break;
			}
			case "Error": {
				Utilities.click(driver, By.xpath(ERROR_BTN_XAPTH));
				break;
			}
			case "Suggestion": {
				Utilities.click(driver, By.xpath(SUGGESTION_BTN_XAPTH));
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + level);
			}
        	Utilities.inputValueAndValidate(driver, By.xpath(COMMENT_TXT_XPATH), comment, comment.length() <= 5000 ? comment : comment.substring(0, 5000));
        	Utilities.click(driver, By.xpath(ADD_BTN_XPATH));
    		Utilities.captureScreen(driver, id);       
            
            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, CREATE_SHEET_NAME, id, "PASS");
        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không hiển thị thông báo lỗi \"" + expectedMsg + "\"";
            Utilities.writeTestResult(FILE_PATH, CREATE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);
            String actual = "Hiển thị thông báo lỗi \"" + Utilities.getActualText(e.getMessage()) + "\"";
            Utilities.writeTestResult(FILE_PATH, CREATE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
	}
	
	public static void save(WebDriver driver, String id, String feedbackType, String yourFeedback, String expectedMsg) throws Exception {
        try {  
        	Utilities.selectDropdownByVisibleText(driver, DROPDOWN_XPATH, feedbackType);
        	if (!feedbackType.equals("AI Only")) {
        		Utilities.inputValueAndValidate(driver, By.xpath(YOUR_FEEDBACK_TXT_XPATH), yourFeedback, yourFeedback.length() <= 10000 ? yourFeedback : yourFeedback.substring(0, 5000));
        	}
//    		Utilities.clickObscuredElement(driver, SAVE_BTN_XPATH, MSG_XPATH);
        	Utilities.click(driver, By.xpath(SAVE_BTN_XPATH));
    		Utilities.captureScreen(driver, id);
            // Thực hiện test
            Utilities.assertTextValue(driver, By.xpath(MSG_XPATH), expectedMsg);
            
            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, CREATE_SHEET_NAME, id, "PASS");
        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không hiển thị thông báo lỗi \"" + expectedMsg + "\"";
            Utilities.writeTestResult(FILE_PATH, CREATE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);
            String actual = "Hiển thị thông báo lỗi \"" + Utilities.getActualText(e.getMessage()) + "\"";
            Utilities.writeTestResult(FILE_PATH, CREATE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
	}
	
	public static void publish(WebDriver driver, String id, String feedbackType, String yourFeedback, String expectedMsg) throws Exception {
		try {  
			Utilities.selectDropdownByVisibleText(driver, DROPDOWN_XPATH, feedbackType);
        	if (!feedbackType.equals("AI Only")) {
        		Utilities.inputValueAndValidate(driver, By.xpath(YOUR_FEEDBACK_TXT_XPATH), yourFeedback, yourFeedback.length() <= 10000 ? yourFeedback : yourFeedback.substring(0, 5000));
        	}
//    		Utilities.clickObscuredElement(driver, PUBLISH_BTN_XPATH, MSG_XPATH);
    		Utilities.click(driver, By.xpath(PUBLISH_BTN_XPATH));
    		Utilities.captureScreen(driver, id);
            // Thực hiện test
            Utilities.assertTextValue(driver, By.xpath(MSG_XPATH), expectedMsg); 
            
            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, CREATE_SHEET_NAME, id, "PASS");
        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không hiển thị thông báo lỗi \"" + expectedMsg + "\"";
            Utilities.writeTestResult(FILE_PATH, CREATE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);
            String actual = "Hiển thị thông báo lỗi \"" + Utilities.getActualText(e.getMessage()) + "\"";
            Utilities.writeTestResult(FILE_PATH, CREATE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
	}
	
	public static void update(WebDriver driver, String id, String level, String comment, String expectedMsg) throws Exception {
        try {  
        	Utilities.click(driver, By.xpath(LINE_BTN_XPATH));
        	switch (level) {
			case "Info": {
				Utilities.click(driver, By.xpath(INFO_BTN_XAPTH));
				break;
			}
			case "Warning": {
				Utilities.click(driver, By.xpath(WARNING_BTN_XAPTH));
				break;
			}
			case "Error": {
				Utilities.click(driver, By.xpath(ERROR_BTN_XAPTH));
				break;
			}
			case "Suggestion": {
				Utilities.click(driver, By.xpath(SUGGESTION_BTN_XAPTH));
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + level);
			}
        	Utilities.inputValueAndValidate(driver, By.xpath(COMMENT_TXT_XPATH), comment, comment.length() <= 5000 ? comment : comment.substring(0, 5000));
        	Utilities.click(driver, By.xpath(ADD_BTN_XPATH));
    		Utilities.captureScreen(driver, id);       
            
            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, UPDATE_SHEET_NAME, id, "PASS");
        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không hiển thị thông báo lỗi \"" + expectedMsg + "\"";
            Utilities.writeTestResult(FILE_PATH, UPDATE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);
            String actual = "Hiển thị thông báo lỗi \"" + Utilities.getActualText(e.getMessage()) + "\"";
            Utilities.writeTestResult(FILE_PATH, UPDATE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
	}
	
	public static void inputYourFeedback(WebDriver driver, By locator, String yourFeedback, String expectedValue) {

	    WebElement textarea = driver.findElement(locator);
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Xóa dữ liệu cũ bằng native setter
	    js.executeScript(
	        "const element = arguments[0];" +
	        "const valueSetter = Object.getOwnPropertyDescriptor(window.HTMLTextAreaElement.prototype,'value').set;" +
	        "valueSetter.call(element, '');" +
	        "element.dispatchEvent(new Event('input', { bubbles: true }));",
	        textarea
	    );

	    // Nhập dữ liệu mới
	    js.executeScript(
	        "const element = arguments[0];" +
	        "const valueSetter = Object.getOwnPropertyDescriptor(window.HTMLTextAreaElement.prototype,'value').set;" +
	        "valueSetter.call(element, arguments[1]);" +
	        "element.dispatchEvent(new Event('input', { bubbles: true }));",
	        textarea,
	        yourFeedback
	    );

	    // So sánh giá trị nhập vào
	    Utilities.assertInputValue(driver, locator, expectedValue);
	}
	
	public static void edit(WebDriver driver, String id, String feedbackType, String yourFeedback, String expectedMsg) throws Exception {
        try {  
        	Utilities.selectDropdownByVisibleText(driver, DROPDOWN_XPATH, feedbackType);
        	if (!feedbackType.equals("AI Only")) {
        		inputYourFeedback(driver, By.xpath(YOUR_FEEDBACK_TXT_XPATH2), yourFeedback, yourFeedback.length() <= 10000 ? yourFeedback : yourFeedback.substring(0, 5000));
        	}
//    		Utilities.clickObscuredElement(driver, SAVE_CHANGES_BTN_XPATH, MSG_XPATH);
        	Utilities.click(driver, By.xpath(SAVE_CHANGES_BTN_XPATH));
    		Utilities.captureScreen(driver, id);
            // Thực hiện test
            Utilities.assertTextValue(driver, By.xpath(MSG_XPATH), expectedMsg); 
            
            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, UPDATE_SHEET_NAME, id, "PASS");
        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không hiển thị thông báo lỗi \"" + expectedMsg + "\"";
            Utilities.writeTestResult(FILE_PATH, UPDATE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);
            String actual = "Hiển thị thông báo lỗi \"" + Utilities.getActualText(e.getMessage()) + "\"";
            Utilities.writeTestResult(FILE_PATH, UPDATE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
	}
	
	public static void delete(WebDriver driver, String id, String expectedMsg) throws Exception {
        try {   
        	Utilities.clickObscuredElement(driver, DELETE_BTN_XPATH, MSG_XPATH);
    		Utilities.captureScreen(driver, id);
            // Thực hiện test
            Utilities.assertTextValue(driver, By.xpath(MSG_XPATH), expectedMsg);         
            
            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, DELETE_SHEET_NAME, id, "PASS");
        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không hiển thị thông báo lỗi \"" + expectedMsg + "\"";
            Utilities.writeTestResult(FILE_PATH, DELETE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);
            String actual = "Hiển thị thông báo lỗi \"" + Utilities.getActualText(e.getMessage()) + "\"";
            Utilities.writeTestResult(FILE_PATH, DELETE_SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
	}
	
}