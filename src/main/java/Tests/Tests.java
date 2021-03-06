package Tests;

import Pages.CartPage;
import Pages.HomePage;
import Provider.ProductsProvider;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;

public class Tests extends BaseTest{
    private double totalPrice;

    @Test(dataProvider = "ProductsProvider", dataProviderClass = ProductsProvider.class)
    public void verifyAddProduct (String productName) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        HomePage homePage = new HomePage(driver);
        homePage.openPage();


        WebElement product = homePage.getProduct(productName);
        double price = Double.parseDouble(product.findElement(By.xpath(".//h5")).getText().substring(1));
        totalPrice = totalPrice + price;
        product.findElement(By.xpath(".//a")).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(., 'Add to cart')]"))).click();

        Thread.sleep(1000);
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        Alert alert = driver.switchTo().alert();
        alert.accept();

        CartPage cartPage = new CartPage(driver);
        cartPage.openPage();

        Assert.assertEquals(cartPage.verifyAddedProducts(productName), true);

        System.out.println("Added product : " + productName + ", cena = " + price);
        System.out.println("Total price = " + totalPrice);
    }

    @Test
    public void verifyTotalPriceInCart () throws InterruptedException {
        CartPage cartPage = new CartPage(driver);
        cartPage.openPage();

        Thread.sleep(1000);

        Assert.assertEquals(cartPage.getTotalPrice(), totalPrice);
    }

    @Test(dataProvider = "ProductsProvider", dataProviderClass = ProductsProvider.class)
    public void verifyRemoveProduct (String productName){

        CartPage cartPage = new CartPage(driver);
        cartPage.openPage();

        double removedProductPrice = cartPage.removeProduct(productName);


        HashMap<String, Integer> cartItemsAfter = cartPage.getItemsFromCart();

        if (!cartItemsAfter.containsKey(productName) || cartItemsAfter.isEmpty()) {
            totalPrice = totalPrice - removedProductPrice;
        }

        Assert.assertEquals(cartPage.verifyRemoveProductFromCart(productName), true);

        System.out.println("Removed product : " + productName + ", cena = " + removedProductPrice);
        System.out.println("Total price = " + totalPrice);
    }

}
