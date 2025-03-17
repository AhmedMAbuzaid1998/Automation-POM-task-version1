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
        return printDocumentsCount(dropdown, "Free document");
    }

    public void printFreeDocuments() {
        printDocuments(dropdown, "Free document");
    }

    public int getPriceDocumentsCount() {
        return printDocumentsCount(getPrice, "Price between $" + minimumPrice + " and $" + maximumPrice + " document");
    }

    public void printPriceDocuments() {
        printDocuments(getPrice, "Price between $" + minimumPrice + " and $" + maximumPrice + " document");
    }

    private void printDocuments(By locator, String message) {
        List<WebElement> elements = driver.findElements(locator);
        for (WebElement element : elements) {
            System.out.println(message + ": " + element.getText());
        }
    }

    private int printDocumentsCount(By locator, String message) {
        List<WebElement> elements = driver.findElements(locator);
        System.out.println(message + " count: " + elements.size());
        return elements.size();
    }
}
