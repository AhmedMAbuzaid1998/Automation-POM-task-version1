package Tests;
import AllPages.Home;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class Test1 {
    WebDriver driver;
    Home homePage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage= new Home(driver);
        homePage.openHomePage();
        homePage.clickGetPaid();
    }

    @Test
    public void testFreeDocuments() {
        int freeDocCount = homePage.getFreeDocumentsCount();
        homePage.printFreeDocuments();
        Assert.assertEquals(freeDocCount, 2, "Mismatch in expected number of free documents.");
    }

    @Test
    public void testPriceDocuments() {
        try {
            int priceDocCount = homePage.getPriceDocumentsCount();
            homePage.printPriceDocuments();
            Assert.assertTrue(priceDocCount > 0, "No price documents found.");
        } catch (Exception e) {
            Assert.fail("Error in testPriceDocuments: " + e.getMessage());
        }
    }

    @AfterClass
    public void shutDown() {
        driver.quit();
    }
}
