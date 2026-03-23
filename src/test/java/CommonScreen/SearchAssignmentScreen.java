package CommonScreen;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import org.testng.Assert;

import Common.Constant;
import Common.Utilities;

public class SearchAssignmentScreen {
    public static final String FILE_PATH				= "src/test/resources/TestData.xlsx";
    public static final String RA_SHEET_NAME			= "ReviewAdjust";
    public static final String BA_SHEET_NAME			= "BulkAdjustment";

    // Tab
    public static final String ASSIGNMENT_TAB_XPATH		= "//span[@class='whitespace-nowrap'][normalize-space()='Assignments']";

    // Text field
    public static final String SEARCH_XPATH	            	= "//input[@placeholder='Search assignments...']";

    // Button
    public static final String DELETED_XPATH		    	= "//button[@id='show-deleted']";
    public static final String EXPORT_XPATH			    	= "(//button[normalize-space()='Export'])[1]";
    public static final String CLEAR_XPATH			    	= "(//button[normalize-space()='Clear Filters'])[1]";
    public static final String ASC_DESC_XPATH		    	= "(//button[normalize-space()='Asc'])[1]";

    // Dropdown
    public static final String STATUS_BTN_XPATH		    	= "//button[@aria-label='Filter by status']";
    public static final String LANGUAGE_BTN_XPATH	    	= "(//button[@aria-label='Filter by language'])[1]";
    public static final String TYPE_BTN_XPATH		    	= "(//button[@aria-label='Filter by assignment type'])[1]";
    public static final String DEADLINE_BTN_XPATH	    	= "(//button[@aria-label='Sort by'])[1]";
    public static final String CREATE_FROM_BTN_XPATH		= "(//button[normalize-space()='Created from'])[1]";
    public static final String CREATE_TO_BTN_XPATH		    = "(//button[normalize-space()='Created to'])[1]";
    public static final String DEADLINE_FROM_BTN_XPATH	    = "(//button[normalize-space()='Deadline from'])[1]";
    public static final String DEADLINE_TO_BTN_XPATH		= "(//button[normalize-space()='Deadline to'])[1]";

    // List
    public static final String LIST_XPATH					= "(//div[@class='relative w-full overflow-x-auto rounded-2xl border border-slate-200/60 dark:border-slate-800/60 bg-white/80 dark:bg-slate-900/80 backdrop-blur-xl shadow-lg'])[1]";

    // Row + fields (from DOM you provided)
    public static final String ROW_XPATH                    = "//tr[@data-slot='table-row']";
    public static final String TITLE_IN_ROW                 = ".//div[contains(@class,'truncate') and contains(@class,'font-semibold')]";
    public static final String LANG_BADGE_IN_ROW            = ".//span[@data-slot='badge' and contains(@class,'rounded-full')][1]";
    public static final String STATUS_BADGE_IN_ROW          = ".//span[@data-slot='badge' and contains(@class,'rounded-full') and (text()='DRAFT' or text()='PUBLISHED' or text()='ARCHIVED' or text()='CLOSED' or text()='INACTIVE')]";
    public static final String DEADLINE_IN_ROW              = ".//span[contains(@class,'text-xs') and contains(@class,'text-red-')]";

    public static WebDriver openSearchAssignmentScreen(String browser) throws Exception {
        WebDriver driver = null;
        if (!browser.isEmpty()) {
            driver = Utilities.getDriver(browser);
            driver.get(Constant.BASE_URL);
            LoginScreen.login(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
            Utilities.clickObscuredElement(driver, HomeScreen.MY_SECTIONS_LINK_XPATH, MySectionsScreen.OPEN_SECTION_XPATH2);
            Utilities.click(driver, By.xpath(MySectionsScreen.OPEN_SECTION_XPATH));
            Utilities.click(driver, By.xpath(ASSIGNMENT_TAB_XPATH));
        }
        return driver;
    }

    public static void search(WebDriver driver, String id, String search, String status, String showDeleted, String type, String languages, String deadline, String clearFilter, String export, String expectedMsg) throws Exception {
        try {
            if (!search.isEmpty()) {
                Utilities.inputValueAndValidate(driver, By.xpath(SEARCH_XPATH), search, search.replace("+", ""));
            }
            if (!status.isEmpty()) {
                Utilities.selectDropdownByVisibleText(driver, STATUS_BTN_XPATH, status);
            }
            if (!showDeleted.isEmpty()) {
                Utilities.setSwitchStatus(driver, DELETED_XPATH, showDeleted);
            }
            if (!type.isEmpty()) {
                Utilities.selectDropdownByVisibleText(driver, TYPE_BTN_XPATH, type);
            }
            if (!languages.isEmpty()) {
                Utilities.selectDropdownByVisibleText(driver, LANGUAGE_BTN_XPATH, languages);
            }
            if (!deadline.isEmpty()) {
                Utilities.selectDropdownByVisibleText(driver, DEADLINE_BTN_XPATH, deadline);
            }
            if (!clearFilter.isEmpty()) {
                Utilities.clickObscuredElement(driver, CLEAR_XPATH, SEARCH_XPATH);
            }
            Utilities.captureScreen(driver, id);

            // Thực hiện test so sánh kết quả lọc
            List<WebElement> rows = driver.findElements(By.xpath(ROW_XPATH));
            for (WebElement row : rows) {
                String title = row.findElement(By.xpath(TITLE_IN_ROW)).getText().toLowerCase();
                String lang = row.findElement(By.xpath(LANG_BADGE_IN_ROW)).getText().toLowerCase();
                String stt = row.findElement(By.xpath(STATUS_BADGE_IN_ROW)).getText().toUpperCase();

                if (!search.isEmpty()) {
                    Assert.assertTrue(title.contains(search.toLowerCase()),
                            "Title không chứa keyword search: " + title);
                }

                if (!languages.isEmpty()) {
                    Assert.assertTrue(lang.contains(languages.toLowerCase()),
                            "Language không đúng: " + lang);
                }

                if (!status.isEmpty()) {
                    Assert.assertEquals(stt, status.toUpperCase(),
                            "Status không đúng");
                }
            }

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