package TestSuite;

import java.lang.reflect.Method;

import CommonScreen.SearchAssignmentScreen;
import DataProvider.SearchData;
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

public class Search extends Initialization {
    @BeforeClass()
    public void setUpClass() throws Exception {
        driver = SearchAssignmentScreen.openSearchAssignmentScreen(browser);
    }

    @BeforeMethod()
    public void setUpMethod(Method method) throws Exception {
        Utilities.testID = method.getName();
        Utilities.refreshScreen(driver);
        Utilities.click(driver, By.xpath(SearchAssignmentScreen.ASSIGNMENT_TAB_XPATH));
    }

    @AfterClass()
    public void tearDownClass() throws Exception {
        Utilities.closeDriver(driver);
    }

    @Test(dataProvider = "searchData", dataProviderClass = SearchData.class)
    public void search(String id, String search, String status, String showDeleted, String type, String languages, String deadline, String clearFilter, String export, String expectedMsg) throws Exception {
        SearchAssignmentScreen.search(driver, id, search, status, showDeleted, type, languages, deadline, clearFilter, export, expectedMsg);
    }
}
