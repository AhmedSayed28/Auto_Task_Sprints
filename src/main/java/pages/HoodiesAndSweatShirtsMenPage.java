package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static utilities.properties.PropertiesManager.WebConfig;

public class HoodiesAndSweatShirtsMenPage {
    private final Driver driver;
    private final String pageUrl = WebConfig.getProperty("BaseURL") + "men/tops-men/hoodies-and-sweatshirts-men.html";

    // Locators
    private final By searchInput = By.id("search");
    private final By productDescription = By.id("description");
    private final By productLinks = By.cssSelector("strong a.product-item-link");
    private final By productLimiterSelect = By.xpath("(//select[@id='limiter'])[2]");
    private final By firstProductCard = By.xpath("//li[@class=\"item product product-item\"][1]");

    public HoodiesAndSweatShirtsMenPage(Driver driver) {
        this.driver = driver;
    }

    public HoodiesAndSweatShirtsMenPage navigateToPage() {
        driver.browser().navigateToURL(pageUrl);
        return this;
    }

    public List<String> searchAndVerifyProductsContainText(String searchQuery) {
        performSearch(searchQuery);
        showAllProducts();
        return getProductsNotContaining(searchQuery);
    }

    public HoodiesAndSweatShirtsMenPage performSearch(String query) {
        driver.element().isDisplayed(searchInput);
        driver.element().fillField(searchInput, query);
        driver.get().findElement(searchInput).sendKeys(Keys.ENTER);
        return this;
    }

    private void showAllProducts() {
        Select dropdown = new Select(driver.get().findElement(productLimiterSelect));
        dropdown.selectByIndex(dropdown.getOptions().size() - 1);
    }

    private List<String> getProductsNotContaining(String searchText) {
        List<WebElement> products = driver.get().findElements(productLinks);

        List<String> nonMatchingProducts = new ArrayList<>();
        String searchLower = searchText.toLowerCase();

        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);
            String productName = product.getText().toLowerCase();

            if (!productName.contains(searchLower)) {
                try {
                    // Click on product to check description
                    product.click();

                    String description = driver.element().getTextOf(productDescription).toLowerCase();
                    if (!description.contains(searchLower)) {
                        nonMatchingProducts.add(productName);
                    }

                    // Navigate back and re-find elements
                    driver.browser().navigateBack();
                    products = driver.get().findElements(productLinks); // Re-find elements after navigation
                    i--; // Reset counter after page reload
                } catch (Exception e) {
                    System.err.println("Error checking product: " + productName);
                    e.printStackTrace();
                    // Continue with next product if error occurs
                    driver.browser().navigateBack();
        }
        return nonMatchingProducts;
    }
        }

        return nonMatchingProducts;
    }

    public HoodiesAndSweatShirtsMenPage selectFirstProduct(){
        driver.element().click(firstProductCard);
        return this;
    }


}