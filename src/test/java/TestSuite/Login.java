package TestSuite;

import java.lang.reflect.Method;

import CommonScreen.LoginEPScreen;
import DataProvider.LoginData;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.Initialization;
import Common.Utilities;
import CommonScreen.AIGradingScreen;
import DataProvider.GradeData;

public class Login extends Initialization {
    @BeforeMethod()
    public void setUpMethod(Method method) throws Exception {
        Utilities.testID = method.getName();
        driver = LoginEPScreen.openLoginEPScreen(browser);
    }

    @AfterMethod()
    public void tearDownMethod() throws Exception {
        Utilities.closeDriver(driver);
    }

    @Test(dataProvider = "loginData", dataProviderClass = LoginData.class)
    public void LoginEP(String id, String email, String password, String expectedMsg) throws Exception {
        LoginEPScreen.checkLogin(driver, id, email, password, expectedMsg);
    }
}
