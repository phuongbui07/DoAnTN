package TestSuite;

import java.lang.reflect.Method;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.Initialization;
import Common.Utilities;
import CommonScreen.ReportsScreen;
import CommonScreen.SectionScreen;
import DataProvider.ReportsData;

public class Reports extends Initialization {
	@BeforeClass()
	public void setUpClass() throws Exception {
		driver = ReportsScreen.openScreen(browser);
	}
	
	@BeforeMethod()
	public void setUpMethod(Method method) throws Exception {
		Utilities.testID = method.getName();
		Utilities.refreshScreen(driver);
		Utilities.click(driver, By.xpath(SectionScreen.REPORTS_TAB_XPATH));
	}
	
	@AfterClass()
	public void tearDownClass() throws Exception {
		Utilities.closeDriver(driver);
	}
	
	@Test(dataProvider = "reportsData", dataProviderClass = ReportsData.class)
	public void checkReports(String id, String reportType, String exportFormat, String FERPACompliant, String maskStudentIDs, String anonymizeData, String expectedMsg) throws Exception {
		ReportsScreen.reports(driver, id, reportType, exportFormat, FERPACompliant, maskStudentIDs, anonymizeData, expectedMsg);
	}
}
