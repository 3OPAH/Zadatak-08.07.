package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartPage extends BasePage{
    private String url;

    public CartPage(WebDriver driver) {
        super(driver);
        this.url = "https://www.demoblaze.com/cart.html";
    }

    public CartPage() {}

    public void openPage () {
        driver.get(this.url);
    }

    public HashMap<String, Integer> getItemsFromCart (){
        HashMap<String, Integer> cartItems = new HashMap<>();
        List<String> header = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.navigate().refresh();

        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("success")));
        }
        catch (TimeoutException e){
            System.out.println("Cart is empty");
            return cartItems;
        }

        WebElement cartTable = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover table-striped']"));

        List<WebElement> headerSpan = cartTable.findElements(By.xpath(".//th"));

        WebElement body = cartTable.findElement(By.xpath(".//tbody"));

        List<WebElement> rows = body.findElements(By.xpath(".//tr"));

        for(int i = 0; i < headerSpan.size(); i++) {
            header.add(headerSpan.get(i).getText());
        }

        for(int i = 0; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.xpath(".//td"));

            for(int j = 0; j < cells.size(); j++) {
                if (header.get(j).equals("Title") && cartItems.containsKey(cells.get(j).getText())){
                    int tempCount = cartItems.get(cells.get(j).getText());
                    cartItems.put(cells.get(j).getText(), tempCount + 1);
                }
                else if (header.get(j).equals("Title") && !cartItems.containsKey(cells.get(j).getText())){
                    cartItems.put(cells.get(j).getText(), 1);
                }
            }

        }


        return cartItems;
    }

    public boolean verifyAddedProducts(String productName) {
        boolean correctlyAdded;
        HashMap<String, Integer> cartItems = this.getItemsFromCart();

        if (!cartItems.containsKey(productName)) {
            correctlyAdded = false;
        } else if (cartItems.get(productName) != 1){
            correctlyAdded = false;
        } else {
            correctlyAdded = true;
        }

        return correctlyAdded;
    }

    public double getTotalPrice () throws InterruptedException {
        double price;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        //Thread.sleep(2000);

        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("success")));
        }
        catch (TimeoutException e){
            System.out.println("Cart is empty");
        }

        if(driver.findElement(By.id("totalp")).getText().length() > 0) {
            price = Double.parseDouble(driver.findElement(By.id("totalp")).getText());
        }else{
            price = 0;
        }

        return price;
    }

    public double removeProduct (String productName){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        List<String> header = new ArrayList<>();
        double removedProductPrice = 0;

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("success")));

        WebElement cartTable = driver.findElement(By.xpath("//table[@class='table table-bordered table-hover table-striped']"));
        WebElement body = cartTable.findElement(By.xpath(".//tbody"));
        List<WebElement> rows = body.findElements(By.xpath(".//tr"));
        List<WebElement> headerSpan = cartTable.findElements(By.xpath(".//th"));

        for(int i = 0; i < headerSpan.size(); i++) {
            header.add(headerSpan.get(i).getText());
        }

        for(int i = 0; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.xpath(".//td"));

            for (int j = 0; j < cells.size(); j++) {
                if (header.get(j).equals("Title") && cells.get(j).getText().equals(productName)) {
                    for (int k = 0; k < cells.size(); k++) {
                        if (header.get(k).equals("Price")) {
                            removedProductPrice = Double.parseDouble(cells.get(k).getText());
                        }
                    }
                    for (int k = 0; k < cells.size(); k++) {
                        if (header.get(k).equals("x")) {
                            cells.get(k).findElement(By.xpath(".//a[contains(., 'Delete')]")).click();
                        }
                    }
                    break;
                }
            }
        }

        return removedProductPrice;
    }

    public boolean verifyRemoveProductFromCart (String productName){
        boolean correctlyRemoved;
        HashMap<String, Integer> cartItems = this.getItemsFromCart();

        if (!cartItems.containsKey(productName) || cartItems.isEmpty()){
            correctlyRemoved = true;
        }else {
            correctlyRemoved = false;
        }

        return correctlyRemoved;
    }

}
