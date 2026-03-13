package TestSuite;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.Initialization;
import Common.Utilities;
import CommonScreen.ReviewAndAdjustScreen;
import CommonScreen.SectionScreen;
import DataProvider.ReviewAdjustData;

public class ReviewAdjust extends Initialization {
	@BeforeClass()
	public void setUpClass() throws Exception {
		driver = ReviewAndAdjustScreen.openReviewAdjustScreen(browser);
	}
	
	@BeforeMethod()
	public void setUpMethod(Method method) throws Exception {
		Utilities.testID = method.getName();
		Utilities.refreshScreen(driver);
		Utilities.click(driver, By.xpath(SectionScreen.REVIEW_TAB_XPATH));
		Utilities.clickObscuredElement(driver, ReviewAndAdjustScreen.SCORE_BTN_XPATH, ReviewAndAdjustScreen.APPLY_BTN_XPATH);
	}
	
	@AfterClass()
	public void tearDownClass() throws Exception {
		Utilities.closeDriver(driver);
	}
	
	@Test(dataProvider = "reviewAdjustData", dataProviderClass = ReviewAdjustData.class)
	public void checkReviewAdjust(String id, String newGrade, String adjustmentReason, String customReason, String justification, String notifyStudent, String expectedMsg) throws Exception {
		ReviewAndAdjustScreen.reviewAdjust(driver, id, newGrade, adjustmentReason, customReason, justification, notifyStudent, expectedMsg);
	}
}
