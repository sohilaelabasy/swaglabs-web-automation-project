package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utilities.Constants;
import utilities.SeleniumUtils;

import java.time.Duration;

public class LandingPage {
    private final WebDriver driver ;
    private static final By PRODUCT_TITLES = By.className("inventory_item_name");
    private static final By CART_BADGE = By.className("shopping_cart_badge");
    private static final By menuButton = By.id("react-burger-menu-btn");
    private static final By logoutButton = By.id("logout_sidebar_link");
    private static final By CART_ICON =
            By.className("shopping_cart_link");
    private static final By SORT_DROPDOWN =
            By.className("product_sort_container");
    private By addToCartButton(String productName){
        return By.id("add-to-cart-" + toProductSlug(productName));
    }

public LandingPage(WebDriver driver){
    this.driver = driver ;
}
    public LandingPage addToCart(String productName){
        By addButton = addToCartButton(productName);
        By removeButton = removeButton(productName);
        SeleniumUtils.click(driver, addButton, Duration.ofSeconds(10));
        try {
            SeleniumUtils.waitVisible(driver, removeButton, Duration.ofSeconds(5));
        } catch (RuntimeException e) {
            WebElement button = SeleniumUtils.waitVisible(driver, addButton, Duration.ofSeconds(5));
            if (driver instanceof org.openqa.selenium.JavascriptExecutor js) {
                js.executeScript("arguments[0].click();", button);
            } else {
                throw e;
            }
            SeleniumUtils.waitVisible(driver, removeButton, Duration.ofSeconds(10));
        }

        return this;
    }
    public int getCartBadgeCount(){

        try {

            String count =
                    SeleniumUtils.waitVisible(driver, CART_BADGE).getText();

            return Integer.parseInt(count);

        } catch (Exception e){

            return 0;
        }
    }
    public CartPage clickCartIcon(){
        WebElement icon = SeleniumUtils.waitClickable(driver, CART_ICON, Duration.ofSeconds(10));
        icon.click();
        try {
            SeleniumUtils.wait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.urlToBe(Constants.Links.CART_PAGE_URL.getValue()));
            return new CartPage(driver);
        } catch (RuntimeException ignored) {
            // Click didn't trigger navigation — try JS click
        }
        WebElement retry = SeleniumUtils.waitClickable(driver, CART_ICON, Duration.ofSeconds(5));
        if (driver instanceof JavascriptExecutor js) {
            js.executeScript("arguments[0].click();", retry);
        } else {
            retry.click();
        }
        SeleniumUtils.wait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(Constants.Links.CART_PAGE_URL.getValue()));
        return new CartPage(driver);
    }
    private By removeButton(String productName){
        return By.id("remove-" + toProductSlug(productName));
    }
    public LandingPage removeFromCart(String productName){
        By removeButton = removeButton(productName);
        SeleniumUtils.click(driver, removeButton, Duration.ofSeconds(10));
        SeleniumUtils.waitVisible(driver, addToCartButton(productName), Duration.ofSeconds(10));

        return this;
    }
    public LandingPage sortProducts(String visibleText){
        WebElement dropdown=
                SeleniumUtils.waitVisible(driver , SORT_DROPDOWN);
        Select select = new Select(dropdown);
        select.selectByVisibleText(visibleText);
        return this;
    }
    public LoginPage clickLogout() {
        WebElement menuBtn = SeleniumUtils.waitClickable(driver, menuButton, Duration.ofSeconds(10));
        new Actions(driver).moveToElement(menuBtn).click().perform();
        WebElement logout = SeleniumUtils.waitClickable(driver, logoutButton, Duration.ofSeconds(10));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logout);
        SeleniumUtils.wait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(Constants.Links.LOGIN_BASE_URL.getValue()));
        return new LoginPage(driver);
    }

    private String toProductSlug(String productName) {
        return productName == null ? "" : productName.trim().toLowerCase()
                .replace(" ", "-")
                .replace(".", "")
                .replace("(", "")
                .replace(")", "");
    }
}
