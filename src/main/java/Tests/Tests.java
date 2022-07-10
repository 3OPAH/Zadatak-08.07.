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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tests extends BaseTest{
    private double totalPrice;
    private List <String> addedProducts = new ArrayList<>();
    private HashMap<String, Integer> cartItems = new HashMap<>();

    @Test(dataProvider = "ProductsProvider", dataProviderClass = ProductsProvider.class)
    public void verifyAddProduct (String productName) throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        HomePage homePage = new HomePage(driver);
        homePage.openPage();


        WebElement product = homePage.getProduct(productName);
        addedProducts.add(product.findElement(By.xpath(".//a[@class='hrefch']")).getText());
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
        cartItems = cartPage.getItemsFromCart();

        Assert.assertEquals(cartPage.validateAddedProducts(cartItems, addedProducts), true);

        System.out.println(productName + ", cena = " + price);
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

        totalPrice = totalPrice - removedProductPrice;

        int cartSize = cartItems.size();

        if (cartItems.containsKey(productName) && cartItems.get(productName) == 1) {
            cartItems.remove(productName);
        }else if (cartItems.get(productName) > 0){
            cartItems.put(productName, cartItems.get(productName) - 1);
        }

        Assert.assertEquals(cartItems.size() == cartSize - 1, true);
    }

}
