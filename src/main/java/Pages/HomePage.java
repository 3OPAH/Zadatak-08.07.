package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage{
    private String url;

    public HomePage (WebDriver driver) {
        super(driver);
        url = "https://www.demoblaze.com/index.html";
    }

    public HomePage () {}

    public void openPage() {
        driver.get(this.url);
    }

    public WebElement getProduct(String productName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'col-lg-4')][contains(., '" + productName + "')]")));
    }
}
