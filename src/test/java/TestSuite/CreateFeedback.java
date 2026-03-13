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
import DataProvider.*;

public class CreateFeedback extends Initialization {
	@BeforeClass()
	public void setUpClass() throws Exception {
		driver = FeedbackScreen.openFeedbackScreen(browser);
	}
	
	@BeforeMethod()
	public void setUpMethod(Method method) throws Exception {
		Utilities.testID = method.getName();
		Utilities.refreshScreen(driver);
		Utilities.click(driver, By.xpath(SectionScreen.FEEDBACK_TAB_XPATH));
	}
	
	@AfterClass()
	public void tearDownClass() throws Exception {
		Utilities.closeDriver(driver);
	}
	
	@Test(dataProvider = "createFeedbackData", dataProviderClass = CreateFeedbackData.class)
	public void checkCreateFeedback(String id, String level, String comment, String expectedMsg) throws Exception {
		FeedbackScreen.create(driver, id, level, comment, expectedMsg);
	}
	
	@Test(dataProvider = "saveFeedbackData", dataProviderClass = SaveFeedbackData.class)
	public void checkSaveFeedback(String id, String feedbackType, String yourFeedback, String expectedMsg) throws Exception {
		FeedbackScreen.save(driver, id, feedbackType, yourFeedback, expectedMsg);
	}
	
	@Test(dataProvider = "publishFeedbackData", dataProviderClass = PublishFeedbackData.class)
	public void checkPublishFeedback(String id, String feedbackType, String yourFeedback, String expectedMsg) throws Exception {
		FeedbackScreen.publish(driver, id, feedbackType, yourFeedback, expectedMsg);
	}
}
