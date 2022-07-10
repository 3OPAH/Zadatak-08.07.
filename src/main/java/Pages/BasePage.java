package Pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;
    private String url;

    public BasePage() {}

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.url = "https://www.demoblaze.com/";
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}