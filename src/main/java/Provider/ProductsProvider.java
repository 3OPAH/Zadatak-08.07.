package Provider;

import org.testng.annotations.DataProvider;

public class ProductsProvider {

    @DataProvider(name = "ProductsProvider")
    public static Object[][] getProductsFromDataProvider(){
        return new Object[][] {
                { "HTC One M9" },
                { "Nokia lumia 1520" },
                { "Sony vaio i7" },
                { "Samsung galaxy s7" }
        };
    }

}
