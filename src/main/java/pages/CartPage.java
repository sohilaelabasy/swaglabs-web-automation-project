package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.Constants;
import utilities.SeleniumUtils;

import java.util.ArrayList;
import java.time.Duration;
import java.util.List;

public class CartPage {
    private final WebDriver driver ;
    //Locators
    private static final By CART_ITEMS = By.className("cart_item");
    private static final By PRODUCT_NAMES =By.className("inventory_item_name");
    private static final By PRODUCT_PRICES = By.className("inventory_item_price");

    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");
    public CartPage(WebDriver driver) {
        this.driver=driver;
    }

    //ACTIONS

    public Checkout clickCheckout(){
        SeleniumUtils.wait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(Constants.Links.CART_PAGE_URL.getValue()));
        WebElement button = SeleniumUtils.waitClickable(driver, CHECKOUT_BUTTON, Duration.ofSeconds(10));
        SeleniumUtils.scrollIntoView(driver, CHECKOUT_BUTTON);
        button.click();
        try {
            SeleniumUtils.wait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.urlToBe(Constants.Links.CHECKOUT_STEP_ONE.getValue()));
            return new Checkout(driver);
        } catch (RuntimeException ignored) {
            // Click didn't trigger navigation — re-find and JS click
        }
        WebElement retry = SeleniumUtils.waitClickable(driver, CHECKOUT_BUTTON, Duration.ofSeconds(5));
        if (driver instanceof JavascriptExecutor js) {
            js.executeScript("arguments[0].click();", retry);
        } else {
            retry.click();
        }
        SeleniumUtils.wait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(Constants.Links.CHECKOUT_STEP_ONE.getValue()));
        return new Checkout(driver);
    }

    public LandingPage clickContinueShopping(){
        SeleniumUtils.click(driver , CONTINUE_SHOPPING_BUTTON);
        return new LandingPage(driver);
    }

    //Validations

    public int getCartItemsCount(){
        return driver.findElements(CART_ITEMS).size();
    }

    public boolean isProductDisplayed(String productName){
        List<WebElement> products =
                driver.findElements(PRODUCT_NAMES);
        for (WebElement product : products){
            if (product.getText().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public void removeProduct(String productName){

        By removeButton =
                By.id("remove-" + toProductSlug(productName));

        SeleniumUtils.click(driver, removeButton);
    }

    public List<String> getProductsNames(){

        List<WebElement> products =
                driver.findElements(PRODUCT_NAMES);

        List<String> names = new ArrayList<>();

        for (WebElement product : products){

            names.add(product.getText());
        }

        return names;
    }

    public List<String> getProductsPrices(){

        List<WebElement> prices =
                driver.findElements(PRODUCT_PRICES);

        List<String> productsPrices = new ArrayList<>();

        for (WebElement price : prices){

            productsPrices.add(price.getText());
        }

        return productsPrices;
    }

    private String toProductSlug(String productName) {
        return productName == null ? "" : productName.trim().toLowerCase()
                .replace(" ", "-")
                .replace(".", "")
                .replace("(", "")
                .replace(")", "");
    }
}
