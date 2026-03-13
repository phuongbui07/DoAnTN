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
import DataProvider.BulkAdjustmentData;

public class BulkAdjustment extends Initialization {
	@BeforeClass()
	public void setUpClass() throws Exception {
		driver = ReviewAndAdjustScreen.openBulkAdjustmentScreen(browser);
	}
	
	@BeforeMethod()
	public void setUpMethod(Method method) throws Exception {
		Utilities.testID = method.getName();
		Utilities.refreshScreen(driver);
		Utilities.click(driver, By.xpath(SectionScreen.REVIEW_TAB_XPATH));
		Utilities.click(driver, By.xpath(ReviewAndAdjustScreen.BULK_ADJ_TAB_XPATH));
	}
	
	@AfterClass()
	public void tearDownClass() throws Exception {
		Utilities.closeDriver(driver);
	}
	
	@Test(dataProvider = "bulkAdjustmentData", dataProviderClass = BulkAdjustmentData.class)
	public void checkBulkAdjustment(String id, String adjustmentType, String points, String justification, String notifyAllAffectedStudents, String expectedMsg) throws Exception {
		ReviewAndAdjustScreen.bulkAdjustment(driver, id, adjustmentType, points, justification, notifyAllAffectedStudents, expectedMsg);
	}
}
