package tests;

import driverFactory.Driver;
import listeners.testng.TestNGListener;
import org.testng.annotations.*;
import pages.HoodiesAndSweatShirtsMenPage;
import pages.ProductDetailsPage;
import pages.CartPage;
import utilities.DataReader;
import utilities.ScreenShotManager;

@Listeners(TestNGListener.class)
public class AddToCartTest {
    private Driver driver;
    private static final String DATA_FILE_PATH = "src/test/resources/testData/Data.json";
    private String productName;

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        DataReader.loadFiles(DATA_FILE_PATH);
    }

    @Test
    public void verifyProductAddedToCartSuccessfully() {
        String searchQuery = DataReader.getValue(DATA_FILE_PATH, "search.query");
        String size = DataReader.getValue(DATA_FILE_PATH, "product.size");

        // Search, select product and add to cart
        new HoodiesAndSweatShirtsMenPage(driver)
                .navigateToPage()
                .performSearch(searchQuery)
                .selectFirstProduct();

        productName = new ProductDetailsPage(driver).getProductName();

        // Configure product and add to cart
        new ProductDetailsPage(driver)
                .chooseSize(size)
                .chooseColor()
                .addToCart()
                .viewCart();

        // Verify product is in cart
        boolean isInCart = new CartPage(driver).isProductInCart(productName);

        if (!isInCart) {
            throw new AssertionError("Product '" + productName + "' was not found in the cart");
        }
    }

    @AfterClass
    public void tearDown() {
        try {
            driver.browser().deleteAllCookies();
        } finally {
            driver.quit();
        }
    }
}