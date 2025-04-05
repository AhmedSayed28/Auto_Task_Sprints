package tests;

import driverFactory.Driver;
import listeners.testng.TestNGListener;
import org.testng.annotations.*;
import pages.HoodiesAndSweatShirtsMenPage;
import utilities.DataReader;
import java.util.List;

@Listeners(TestNGListener.class)
public class ProductSearchTest {

    private Driver driver;
    private static final String DATA_FILE_PATH = "src/test/resources/testData/Data.json";

    @BeforeClass
    public void setUp() {
        driver = new Driver();
        DataReader.loadFiles(DATA_FILE_PATH);
    }

    @Test
    public void verifyAllProductsContainSearchTerm() {
        String searchQuery = DataReader.getValue(DATA_FILE_PATH, "search.query");
        HoodiesAndSweatShirtsMenPage page = new HoodiesAndSweatShirtsMenPage(driver);

        List<String> nonMatchingProducts = page.navigateToPage()
                .searchAndVerifyProductsContainText(searchQuery);

        if (!nonMatchingProducts.isEmpty()) {
            String errorMessage = String.format("%d products don't contain '%s': %s",
                    nonMatchingProducts.size(),
                    searchQuery,
                    String.join(", ", nonMatchingProducts));
            throw new AssertionError(errorMessage);
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