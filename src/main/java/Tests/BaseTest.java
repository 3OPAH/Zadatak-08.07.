package Tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected static WebDriver driver;
    protected String url;

    @BeforeClass
    public void setup() {
        //System.setProperty("webdriver.chrome.driver", "C://Users//3OPAH//JavaBiblioteke/chromedriver.exe");
        //this.driver = new ChromeDriver();
        //System.setProperty("webdriver.gecko.driver", "C://Users//3OPAH//JavaBiblioteke/geckodriver.exe");
        //this.driver = new FirefoxDriver();
        System.setProperty("webdriver.edge.driver", "C://Users//3OPAH//JavaBiblioteke/msedgedriver.exe");
        this.driver = new EdgeDriver();
        driver.manage().window().maximize();

    }

    @AfterClass
    public void afterClass() {
        this.driver.quit();
    }

}