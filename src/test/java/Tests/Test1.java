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
        System.out.println("Free documents: " + freeDocCount);
        homePage.printFreeDocuments();
        Assert.assertEquals(freeDocCount, 2, "Mismatch in expected number of free documents.");
        Assert.assertEquals(homePage.freeElements.get(0).getText(), "Exchange a Waiver");
        Assert.assertEquals(homePage.freeElements.get(1).getText(), "Send a Payment Document");
    }


    @Test
    public void testPriceDocuments() {

            int priceDocCount = homePage.getPriceDocumentsCount();
            System.out.println("Price documents: " + priceDocCount);
            homePage.printPriceDocuments();
            Assert.assertEquals(priceDocCount, 1);
            Assert.assertEquals(homePage.pricedElements.get(0).getText(), "Send a Warning");
    }

    @AfterClass
    public void shutDown() {
        driver.quit();
    }
}
