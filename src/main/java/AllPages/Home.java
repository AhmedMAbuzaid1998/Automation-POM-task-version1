package AllPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class Home {
    WebDriver driver;
    WebDriverWait wait;
    public List<WebElement> freeElements ;
    public List<WebElement> pricedElements ;
    public Home(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    String getPaidBtn = "Get paid ";
    String price = "Free";
    int minimumPrice = 30, maximumPrice = 60;

    By getPaid = By.xpath(String.format("//a[contains(text(),'%s')]/ancestor::li", getPaidBtn));
    By dropdown = By.xpath(String.format("//span[contains(@class,'price-amount') and contains(text(),'%s')]/ancestor::div[@class='panel-heading left-right-pair']//div[@class='left']", price));
    By getPrice = By.xpath(String.format("//span[contains(@class,'price-amount') and number(translate(text(),'$','')>=%d) and number(translate(text(),'$','')<=%d)]/ancestor::div[@class='panel-heading left-right-pair']//div[@class='left']", minimumPrice, maximumPrice));

    public void openHomePage() {
        try {
            driver.get("https://www.levelset.com/");
            wait.until(ExpectedConditions.presenceOfElementLocated(getPaid));
            Assert.assertTrue(driver.findElement(getPaid).isEnabled(), "Home page not loaded properly.");
            System.out.println("Home page successfully opened.");
        } catch (Exception e) {
            Assert.fail("Cannot open home page: " + e.getMessage());
        }
    }

    public void clickGetPaid() {
        try {
            Actions action = new Actions(driver);
            action.doubleClick(driver.findElement(getPaid)).perform();
            wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));
            Assert.assertTrue(driver.findElement(dropdown).isDisplayed(), "Dropdown not displayed.");
            System.out.println("Get Paid button successfully clicked.");
        } catch (Exception e) {
            Assert.fail("Cannot click on Get Paid: " + e.getMessage());
        }
    }

    public int getFreeDocumentsCount() {
        freeElements = driver.findElements(dropdown);
        return freeElements.size();
    }

    public void printFreeDocuments() {
        freeElements = driver.findElements(dropdown);
        for (WebElement document : freeElements) {
            System.out.println("Free document: " + document.getText());
        }
    }

    public int getPriceDocumentsCount() {
        pricedElements = driver.findElements(getPrice);
        return pricedElements.size();
    }

    public void printPriceDocuments() {
        pricedElements = driver.findElements(getPrice);
        for (WebElement document : pricedElements) {
            System.out.println("Price document between $" + minimumPrice + " and $" + maximumPrice
                   + ":"+ document.getText());
        }
    }
}
