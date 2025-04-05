package tests;

import driverFactory.Driver;
import listeners.testng.TestNGListener;
import org.testng.annotations.*;
import pages.*;
import utilities.DataReader;
import utilities.ScreenShotManager;

@Listeners(TestNGListener.class)
public class CheckoutProcessTest {
    private Driver driver;
    private static final String DATA_FILE_PATH = "src/test/resources/testData/Data.json";
    private String productName;

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        DataReader.loadFiles(DATA_FILE_PATH);
    }

    @Test
    public void verifyCompleteCheckoutProcess() {
        // Test data
        String searchQuery = DataReader.getValue(DATA_FILE_PATH, "search.query");
        String size = DataReader.getValue(DATA_FILE_PATH, "product.size");
        String email = DataReader.getValue(DATA_FILE_PATH, "checkout.email");
        String firstName = DataReader.getValue(DATA_FILE_PATH, "checkout.firstName");
        String lastName = DataReader.getValue(DATA_FILE_PATH, "checkout.lastName");
        String address = DataReader.getValue(DATA_FILE_PATH, "checkout.address");
        String city = DataReader.getValue(DATA_FILE_PATH, "checkout.city");
        String zip = DataReader.getValue(DATA_FILE_PATH, "checkout.zip");
        String phone = DataReader.getValue(DATA_FILE_PATH, "checkout.phone");
        String successfulMessage = DataReader.getValue(DATA_FILE_PATH, "checkout.successMSG");

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

       new CartPage(driver)
                .proceedToCheckout();

        String confirmationMessage = new CheckoutPage(driver)
                .fillShippingInformation(email, firstName, lastName, address, city, zip, phone)
                .selectShippingMethod()
                .goToPayment()
                .placeOrder()
                .getOrderConfirmationMessage();

        // Verification
        if (!confirmationMessage.contains(successfulMessage)) {
            throw new AssertionError("Checkout failed. Expected confirmation message not found. Actual: " + confirmationMessage);
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