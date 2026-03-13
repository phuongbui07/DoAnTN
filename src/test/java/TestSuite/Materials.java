package TestSuite;

import java.lang.reflect.Method;

import CommonScreen.MaterialScreen;
import DataProvider.MaterialData;
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

public class Materials extends Initialization {
    @BeforeClass()
    public void setUpClass() throws Exception {
        driver = MaterialScreen.openEditMaterialScreen(browser);
    }

    @BeforeMethod()
    public void setUpMethod(Method method) throws Exception {
        Utilities.testID = method.getName();
        Utilities.refreshScreen(driver);
        Utilities.click(driver, By.xpath(MaterialScreen.MATERIALS_TAB_XPATH));
        Utilities.clickObscuredElement(driver, MaterialScreen.SELECT_MATERIALS_BTN_XPATH, MaterialScreen.EDIT_MATERIALS_BTN_XPATH);
    }

    @AfterClass()
    public void tearDownClass() throws Exception {
        Utilities.closeDriver(driver);
    }

    @Test(dataProvider = "materialData", dataProviderClass = MaterialData.class)
    public void checkMaterials(String id, String status, String type, String visibility, String folder, String addTag, String AllowDownloads, String AllowPreviews, String AvailableFrom, String AvailableUntil, String Note, String Notify, String expectedMsg) throws Exception {
        MaterialScreen.materials(driver, id, status, type, visibility, folder, addTag, AllowDownloads, AllowPreviews, AvailableFrom, AvailableUntil, Note, Notify, expectedMsg);
    }
}
