package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utilities.SeleniumUtils;

import java.util.ArrayList;
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
        SeleniumUtils.click(driver , CHECKOUT_BUTTON);
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
                By.id("remove-" + productName);

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
}
