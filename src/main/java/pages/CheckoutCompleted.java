package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.Constants;
import utilities.SeleniumUtils;

public class CheckoutCompleted {
    private final WebDriver driver;

    private static final By COMPLETE_HEADER = By.cssSelector("[data-test='complete-header']");

    public CheckoutCompleted(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isOnCheckoutCompleteUrl() {
        return Constants.Links.COMPLETE_ORDER_PAGE_URL.getValue().equals(driver.getCurrentUrl());
    }

    public boolean isCompleteHeaderVisible() {
        try {
            WebElement element = SeleniumUtils.waitVisible(driver, COMPLETE_HEADER);
            return element.isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }
    public boolean isCheckoutCompleted() {
        return isCompleteHeaderVisible() && isOnCheckoutCompleteUrl();
    }

}
