package TestSuite;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.Initialization;
import Common.Utilities;
import CommonScreen.AIGradingScreen;
import DataProvider.GradeData;

public class Grade extends Initialization {
	@BeforeMethod()
	public void setUpMethod(Method method) throws Exception {
		Utilities.testID = method.getName();
		driver = AIGradingScreen.openScreen(browser);
	}
	
	@AfterMethod()
	public void tearDownMethod() throws Exception {
		Utilities.closeDriver(driver);
	}
	
	@Test(dataProvider = "gradeData", dataProviderClass = GradeData.class)
	public void checkGrade(String id, String assignment, String expectedMsg) throws Exception {
		AIGradingScreen.grade(driver, id, assignment, expectedMsg);
	}
}
