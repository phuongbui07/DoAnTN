package TestSuite;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.Initialization;
import Common.Utilities;
import CommonScreen.FeedbackScreen;
import CommonScreen.SectionScreen;
import DataProvider.DeleteFeedbackData;

public class DeleteFeedback extends Initialization {
	@BeforeClass()
	public void setUpClass() throws Exception {
		driver = FeedbackScreen.openDrafsScreen(browser);
	}
	
	@BeforeMethod()
	public void setUpMethod(Method method) throws Exception {
		Utilities.testID = method.getName();
		Utilities.refreshScreen(driver);
		Utilities.click(driver, By.xpath(SectionScreen.FEEDBACK_TAB_XPATH));
		Utilities.click(driver, By.xpath(FeedbackScreen.DRAFTS_TAB_XPATH));
	}
	
	@AfterClass()
	public void tearDownClass() throws Exception {
		Utilities.closeDriver(driver);
	}
	
	@Test(dataProvider = "deleteFeedbackData", dataProviderClass = DeleteFeedbackData.class)
	public void checkDeleteFeedback(String id, String expectedMsg) throws Exception {
		FeedbackScreen.delete(driver, id, expectedMsg);
	}
}
