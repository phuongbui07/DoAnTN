package CommonScreen;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Common.Constant;
import Common.Utilities;

public class LoginScreen {
	// Text field
	public static final String EMAIL_TXT_ID				= "email";
	public static final String PASSWORD_TXT_ID			= "password";
	
	// Button
	public static final String LOGIN_BTN_XPATH			= "//button[normalize-space()='Sign in']";

	// Msg
	public static final String NO_PASSWORD		= "//p[@role='alert']";
	public static final String NO_EMAIL		= "//p[@role='alert']";

	
	public static WebDriver openScreen(String browser) {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
		}
		return driver;
	}
	
	public static void login(WebDriver driver, String email, String password) throws Exception {
		Utilities.inputValueAndValidate(driver, By.id(EMAIL_TXT_ID), email, email);
		Utilities.inputValueAndValidate(driver, By.id(PASSWORD_TXT_ID), password, password);
		Utilities.click(driver, By.xpath(LOGIN_BTN_XPATH));
		TimeUnit.SECONDS.sleep(Constant.WAIT_REFRESH_SCREEN);
	}
}