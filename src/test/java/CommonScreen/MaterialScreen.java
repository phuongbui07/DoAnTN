package CommonScreen;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import Common.Constant;
import Common.Utilities;

public class MaterialScreen {
    public static final String FILE_PATH				= "src/test/resources/TestData.xlsx";
    public static final String MATERIALS_SHEET_NAME		= "EditMaterials";

    // Tab
    public static final String MATERIALS_TAB_XPATH		= "//span[normalize-space()='Materials']";

    // Button
    public static final String CHECK_MATERIALS_BTN_XPATH	= "//tbody/tr[1]";
    public static final String EDIT_MATERIALS_BTN_XPATH	    = "//button[normalize-space()='Bulk Edit']";
    public static final String ADD_TAG_BTN_XPATH			= "//button[normalize-space()='Add']";
    public static final String START_DATE_BTN_XPATH		    = "//input[@id='bulk-available-from']";
    public static final String END_DATE_BTN_XPATH		    = "//input[@id='bulk-available-until']";
    public static final String SAVE_BTN_XPATH			    = "(//button[normalize-space()='Update 1 Material'])[1]";
    public static final String CANCEL_BTN_XPATH			    = "//button[normalize-space()='Cancel']";
    public static final String NOTIFY_BTN_XPATH			    = "(//button[@role='switch'])[1]";
    public static final String SELECT_MATERIALS_BTN_XPATH	= "(//div[@class='flex-1 flex flex-col space-y-4']//div[2]//div//div//div//table//tbody//tr//td//button)[1]";;

    // Combobox/Dropdown
    public static final String STATUS_DROPDOWN_XPATH	    = "//label[normalize-space()='Status']/following-sibling::button[@role='combobox']";
    public static final String TYPE_DROPDOWN_XPATH		    = "//label[normalize-space()='Material Type']/following-sibling::button[@role='combobox']";
    public static final String VISIBILITY_DROPDOWN_XPATH	= "//label[normalize-space()='Visibility']/following-sibling::button[@role='combobox']";
    public static final String FOLDER_DROPDOWN_XPATH	    = "//label[normalize-space()='Folder']/following-sibling::button[@role='combobox']";
    public static final String ADD_TAG_TXT_XPATH		    = "(//input[@placeholder='Add tag...'])[1]";
    public static final String DOWNLOAD_DROPDOWN_XPATH	    = "(//button[@role='combobox'])[11]";
    public static final String PREVIEW_DROPDOWN_XPATH	    = "(//button[@role='combobox'])[12]";
    public static final String NOTE_TXT_XPATH	            = "(//textarea[@id='bulk-change-summary'])[1]";

    // Message
    public static final String MSG_XPATH				    = "(//div[normalize-space()='Bulk update completed'])[1]";

    public static WebDriver openEditMaterialScreen(String browser) throws Exception {
        WebDriver driver = null;
        if (!browser.isEmpty()) {
            driver = Utilities.getDriver(browser);
            driver.get(Constant.BASE_URL);
            LoginScreen.login(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
            Utilities.clickObscuredElement(driver, HomeScreen.MY_SECTIONS_LINK_XPATH, MySectionsScreen.OPEN_SECTION_XPATH2);
            Utilities.click(driver, By.xpath(MySectionsScreen.OPEN_SECTION_XPATH));
            Utilities.click(driver, By.xpath(MATERIALS_TAB_XPATH));
            Utilities.click(driver, By.xpath(SELECT_MATERIALS_BTN_XPATH));
            //Utilities.click(driver, By.xpath(EDIT_MATERIALS_BTN_XPATH));
            Utilities.clickObscuredElement(driver, EDIT_MATERIALS_BTN_XPATH, SAVE_BTN_XPATH);
        }
        return driver;
    }

    public static void materials(WebDriver driver, String id, String status, String type, String visibility, String folder, String addTag, String AllowDownloads, String AllowPreviews, String AvailableFrom, String AvailableUntil, String Note, String Notify, String expectedMsg) throws Exception {
        try {
            Utilities.selectDropdownByVisibleText(driver, STATUS_DROPDOWN_XPATH, status);
            Utilities.selectDropdownByVisibleText(driver, TYPE_DROPDOWN_XPATH, type);
            Utilities.selectDropdownByVisibleText(driver, VISIBILITY_DROPDOWN_XPATH, visibility);
            Utilities.selectDropdownByVisibleText(driver, FOLDER_DROPDOWN_XPATH, folder);
            if (!addTag.isEmpty()) {
                Utilities.inputValueAndValidate(driver, By.xpath(ADD_TAG_TXT_XPATH), addTag, addTag.length() <= 255 ? addTag : addTag.substring(0, 255));
                Utilities.clickObscuredElement(driver, ADD_TAG_BTN_XPATH, ADD_TAG_TXT_XPATH);
            }
            Utilities.selectDropdownByVisibleText(driver, DOWNLOAD_DROPDOWN_XPATH, AllowDownloads);
            Utilities.selectDropdownByVisibleText(driver, PREVIEW_DROPDOWN_XPATH, AllowPreviews);

            // ===== Available From =====
            if (!AvailableFrom.isEmpty()) {

                // tách time và date
                String[] partsFrom = AvailableFrom.split(" ");
                String timeFrom = partsFrom[0];       // 17:52
                String dateFrom = partsFrom[1];       // 08/03/2026

                String dayFrom = dateFrom.split("/")[0];   // 08
                String hourFrom = timeFrom.split(":")[0];  // 17
                String minuteFrom = timeFrom.split(":")[1];// 52

                Utilities.clickObscuredElement(driver, START_DATE_BTN_XPATH, START_DATE_BTN_XPATH);

                // chọn ngày
                Utilities.clickObscuredElement(driver, "//button[text()='" + Integer.parseInt(dayFrom) + "']", START_DATE_BTN_XPATH);

                // chọn giờ
                Utilities.clickObscuredElement(driver, "//div[text()='" + hourFrom + "']", START_DATE_BTN_XPATH);

                // chọn phút
                Utilities.clickObscuredElement(driver, "//div[text()='" + minuteFrom + "']", START_DATE_BTN_XPATH);
            }


            // ===== Available Until =====
            if (!AvailableUntil.isEmpty()) {

                String[] partsUntil = AvailableUntil.split(" ");
                String timeUntil = partsUntil[0];
                String dateUntil = partsUntil[1];

                String dayUntil = dateUntil.split("/")[0];
                String hourUntil = timeUntil.split(":")[0];
                String minuteUntil = timeUntil.split(":")[1];

                Utilities.clickObscuredElement(driver, END_DATE_BTN_XPATH, END_DATE_BTN_XPATH);

                Utilities.clickObscuredElement(driver, "//button[text()='" + Integer.parseInt(dayUntil) + "']", END_DATE_BTN_XPATH);

                Utilities.clickObscuredElement(driver, "//div[text()='" + hourUntil + "']", END_DATE_BTN_XPATH);

                Utilities.clickObscuredElement(driver, "//div[text()='" + minuteUntil + "']", END_DATE_BTN_XPATH);
            }

            Utilities.inputValueAndValidate(driver, By.xpath(NOTE_TXT_XPATH), Note, Note.length() <= 2000 ? Note : Note.substring(0, 2000));
            Utilities.setSwitchStatus(driver, NOTIFY_BTN_XPATH, Notify);
            Utilities.clickObscuredElement(driver, SAVE_BTN_XPATH, MSG_XPATH);
            Utilities.captureScreen(driver, id);
            // Thực hiện test
            Utilities.assertTextValue(driver, By.xpath(MSG_XPATH), expectedMsg);

            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, MATERIALS_SHEET_NAME, id, "PASS");
        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không hiển thị thông báo lỗi \"" + expectedMsg + "\"";
            Utilities.writeTestResult(FILE_PATH, MATERIALS_SHEET_NAME, id, "FAIL", actual);
            throw e;
        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);
            String actual = "Hiển thị thông báo lỗi \"" + Utilities.getActualText(e.getMessage()) + "\"";
            Utilities.writeTestResult(FILE_PATH, MATERIALS_SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
    }


}
