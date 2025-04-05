package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.List;
import java.util.stream.Collectors;

import static utilities.properties.PropertiesManager.WebConfig;

public class HoodiesAndSweatShirtsMenPage {
    private final Driver driver;
    private final String pageUrl = WebConfig.getProperty("BaseURL") + "men/tops-men/hoodies-and-sweatshirts-men.html";

    // Locators
    private final By searchInput = By.id("search");
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

        return products.stream()
                .map(WebElement::getText)
                .filter(name -> !name.toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public HoodiesAndSweatShirtsMenPage selectFirstProduct(){
        driver.element().click(firstProductCard);
        return this;
    }


}