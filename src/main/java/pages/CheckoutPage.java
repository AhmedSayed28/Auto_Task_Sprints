package pages;

import driverFactory.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage {
    private final Driver driver;

    // Locators
    private final By emailField = By.xpath("//div[@class='control _with-tooltip']//input[@id='customer-email']");
    private final By firstNameField = By.name("firstname");
    private final By lastNameField = By.name("lastname");
    private final By streetAddressField = By.name("street[0]");
    private final By cityField = By.name("city");
    private final By stateField = By.name("region_id");
    private final By zipCodeField = By.name("postcode");
    private final By countryField = By.name("country_id");
    private final By phoneField = By.name("telephone");
    private final By shippingMethod = By.xpath("//input[@value=\"flatrate_flatrate\"]");
    private final By nextButton = By.xpath("//span[normalize-space()='Next']");
    private final By confirmAddressCheckBox = By.id("billing-address-same-as-shipping-checkmo");
    private final By placeOrderBtn = By.xpath("//span[normalize-space()='Place Order']");
    private final By contShoppingBtn = By.xpath("//span[normalize-space()='Continue Shopping']");
    private final By orderSuccessMsg = By.xpath("//span[@data-ui-id=\"page-title-wrapper\"]");

    public CheckoutPage(Driver driver) {
        this.driver = driver;
    }

    public CheckoutPage fillShippingInformation(String email, String firstName, String lastName,
                                                String address, String city, String zip, String phone) {
        driver.element().fillField(emailField, email);
        driver.element().fillField(firstNameField, firstName);
        driver.element().fillField(lastNameField, lastName);
        driver.element().fillField(streetAddressField, address);
        driver.element().fillField(cityField, city);
        driver.element().selectByIndex(stateField,1);
        driver.element().selectByIndex(countryField,1);
        driver.element().fillField(zipCodeField, zip);
        driver.element().fillField(phoneField, phone);

        return this;
    }

    public CheckoutPage selectShippingMethod() {
        driver.element().click(shippingMethod);
        return this;
    }

    public CheckoutPage goToPayment() {
        driver.element().click(nextButton);
        return this;
    }

    public CheckoutPage placeOrder() {
        driver.element().click(confirmAddressCheckBox);
        driver.element().click(placeOrderBtn);
        return this;
    }

    public String getOrderConfirmationMessage() {
        driver.element().isClickable(contShoppingBtn);
        return driver.element().getTextOf(orderSuccessMsg);
    }
}