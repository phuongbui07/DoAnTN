package CommonScreen;

import org.openqa.selenium.WebDriver;

import Common.Constant;
import Common.Utilities;

public class HomeScreen {
	public static final String MY_SECTIONS_LINK_XPATH		= "//a[@href='/instructor/sections']";

	public static WebDriver openScreen(String browser) throws Exception {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			LoginScreen.login(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
		}
		return driver;
	}
}
