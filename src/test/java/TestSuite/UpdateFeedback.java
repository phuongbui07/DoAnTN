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
import DataProvider.UpdateFeedbackData;
import DataProvider.EditFeedbackData;

public class UpdateFeedback extends Initialization {
	@BeforeClass()
	public void setUpClass() throws Exception {
		driver = FeedbackScreen.openEditScreen(browser);
	}
	
	@BeforeMethod()
	public void setUpMethod(Method method) throws Exception {
		Utilities.testID = method.getName();
		Utilities.refreshScreen(driver);
		Utilities.click(driver, By.xpath(SectionScreen.FEEDBACK_TAB_XPATH));
		Utilities.click(driver, By.xpath(FeedbackScreen.DRAFTS_TAB_XPATH));
		Utilities.click(driver, By.xpath(FeedbackScreen.EDIT_BTN_XPATH));
	}
	
	@AfterClass()
	public void tearDownClass() throws Exception {
		Utilities.closeDriver(driver);
	}
	
//	@Test(dataProvider = "updateFeedbackData", dataProviderClass = UpdateFeedbackData.class)
	public void checkUpdateFeedback(String id, String level, String comment, String expectedMsg) throws Exception {
		FeedbackScreen.update(driver, id, level, comment, expectedMsg);
	}
	
	@Test(dataProvider = "editFeedbackData", dataProviderClass = EditFeedbackData.class)
	public void checkEditFeedback(String id, String feedbackType, String yourFeedback, String expectedMsg) throws Exception {
		FeedbackScreen.edit(driver, id, feedbackType, yourFeedback, expectedMsg);
	}
}
