package browserActions;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class BrowserActions {
    private WebDriver driver;

    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    /***************************  URL Controlling and navigation  ***********************************/

    public BrowserActions navigateToURL(String url) {
        driver.navigate().to(url);
        return this;
    }



    public BrowserActions deleteAllCookies() {
        driver.manage().deleteAllCookies();
        return this;
    }




}