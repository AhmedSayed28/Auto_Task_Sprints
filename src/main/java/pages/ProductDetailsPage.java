package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;

public class ProductDetailsPage {
    private final Driver driver;

    // Locators
    private final By addToCartBtn = By.id("product-addtocart-button");
    private final By firstColorSelector = By.xpath("(//div[@class='swatch-option color'])[1]");
    private final By viewCartLink = By.xpath("//div[@role=\"alert\"] //a[@href=\"https://magento.softwaretestingboard.com/checkout/cart/\"]");
    private final By productName = By.cssSelector(".page-title span");

    public ProductDetailsPage(Driver driver) {
        this.driver = driver;
    }

    public ProductDetailsPage chooseSize(String size){
        size = size.toUpperCase();
        driver.element().click(By.xpath("//div[text()='"+size+"'][@class=\"swatch-option text\"]"));
        return this;
    }

    public ProductDetailsPage chooseColor(){
        driver.element().click(firstColorSelector);
        return this;
    }

    public ProductDetailsPage addToCart() {
        driver.element().click(addToCartBtn);
        return this;
    }

    public ProductDetailsPage viewCart() {
        driver.element().click(viewCartLink);
        return this;
    }

    public String getProductName() {
        return driver.element().getTextOf(productName);
    }
}
