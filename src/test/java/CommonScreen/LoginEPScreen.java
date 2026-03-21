package CommonScreen;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import Common.Constant;
import Common.Utilities;

import java.util.concurrent.TimeUnit;

public class LoginEPScreen {

    public static final String FILE_PATH				= "src/test/resources/TestData.xlsx";
    public static final String LOGIN_SHEET_NAME			= "Login";
    // Input IDs
    public static final String EMAIL_TXT_ID = "email";
    public static final String PASSWORD_TXT_ID = "password";

    // Button
    public static final String LOGIN_BTN_XPATH = "//button[normalize-space()='Sign in']";

    // Inline validation messages under each field (red text under inputs)
    public static final String EMAIL_ERROR_XPATH = "//p[@role='alert']";
    public static final String PASSWORD_ERROR_XPATH = "//p[@role='alert']";

    public static WebDriver openLoginEPScreen(String browser) {
        WebDriver driver = null;
        if (browser != null && !browser.isEmpty()) {
            driver = Utilities.getDriver(browser);
            driver.get(Constant.BASE_URL);
        }
        return driver;
    }

    public static void checkLogin(WebDriver driver, String id, String email, String password, String expectedMsg) throws Exception {
        try {
            // Input (clear trước để không dính case trước)
            Utilities.clearInput(driver, By.id(EMAIL_TXT_ID));
            Utilities.clearInput(driver, By.id(PASSWORD_TXT_ID));

            if (email != null && !email.isBlank()) {
                Utilities.sendKeys(driver, By.id(EMAIL_TXT_ID), email);
            }
            if (password != null && !password.isBlank()) {
                Utilities.sendKeys(driver, By.id(PASSWORD_TXT_ID), password);
            }

            Utilities.click(driver, By.xpath(LOGIN_BTN_XPATH));
            TimeUnit.SECONDS.sleep(Constant.WAIT_REFRESH_SCREEN);

            Utilities.captureScreen(driver, id);

            String url = driver.getCurrentUrl();
            boolean isRedirected = url.contains("/instructor");

            if ("Login thành công".equalsIgnoreCase(expectedMsg)) {
                // PASS nếu redirect sang instructor
                org.testng.Assert.assertTrue(isRedirected,
                        "Expected redirect to /instructor but URL=" + url);
            } else if ("Login thất bại".equalsIgnoreCase(expectedMsg)) {
                // PASS nếu KHÔNG redirect sang instructor
                org.testng.Assert.assertFalse(isRedirected,
                        "Expected stay on /login but redirected. URL=" + url);
            } else if (expectedMsg == null || expectedMsg.isBlank()) {
                // Nếu bạn muốn dùng rule "expectedMsg rỗng => PASS"
                org.testng.Assert.assertTrue(isRedirected,
                        "Expected redirect to /instructor but URL=" + url);
            } else {
                org.testng.Assert.fail("expectedMsg không hợp lệ: \"" + expectedMsg
                        + "\". Hãy dùng: 'Login thành công' hoặc 'Login thất bại' (hoặc để trống cho case PASS).");
            }

            // Nếu thành công
            Utilities.writeTestResult(FILE_PATH, LOGIN_SHEET_NAME, id, "PASS");

        } catch (NoSuchElementException e) {
            Utilities.captureScreen(driver, id);
            String actual = "Không tìm thấy element khi login (NoSuchElementException). " + e.getMessage();
            Utilities.writeTestResult(FILE_PATH, LOGIN_SHEET_NAME, id, "FAIL", actual);
            throw e;

        } catch (AssertionError e) {
            Utilities.captureScreen(driver, id);

            // Ghi actualResult dễ hiểu hơn (lấy thêm msg đỏ nếu có)
            String url = "";
            try { url = driver.getCurrentUrl(); } catch (Exception ignored) {}

            String emailErr = "";
            String passErr = "";
            try {
                if (Utilities.checkElementVisible(driver, By.xpath(EMAIL_ERROR_XPATH))) {
                    emailErr = driver.findElement(By.xpath(EMAIL_ERROR_XPATH)).getText().trim();
                }
            } catch (Exception ignored) {}

            try {
                if (Utilities.checkElementVisible(driver, By.xpath(PASSWORD_ERROR_XPATH))) {
                    passErr = driver.findElement(By.xpath(PASSWORD_ERROR_XPATH)).getText().trim();
                }
            } catch (Exception ignored) {}

            String actual = "Kết quả thực tế không đúng. URL=" + url
                    + (emailErr.isBlank() ? "" : (" | EmailErr=\"" + emailErr + "\""))
                    + (passErr.isBlank() ? "" : (" | PasswordErr=\"" + passErr + "\""));

            Utilities.writeTestResult(FILE_PATH, LOGIN_SHEET_NAME, id, "FAIL", actual);
            throw e;
        }
    }
}