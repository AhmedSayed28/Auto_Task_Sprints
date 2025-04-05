package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage {
    private final Driver driver;

    // Locators
    private final By cartItems = By.cssSelector("strong[class='product-item-name'] a");
    private final By proceedToCheckoutBtn = By.cssSelector("button[data-role='proceed-to-checkout']");

    public CartPage(Driver driver) {
        this.driver = driver;
    }

    public boolean isProductInCart(String productName) {
        List<WebElement> items = driver.get().findElements(cartItems);
        return items.stream()
                .anyMatch(item -> item.getText().contains(productName));
    }

    public CartPage proceedToCheckout() {
        driver.element().click(proceedToCheckoutBtn);
        return this;
    }
}
