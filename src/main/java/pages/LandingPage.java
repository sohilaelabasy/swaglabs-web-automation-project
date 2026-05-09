package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utilities.SeleniumUtils;

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
        return By.xpath(
                "//div[contains(text(),'"+productName+ "')]/ancestor::div[@class='inventory_item']//button"
        );
    }

public LandingPage(WebDriver driver){
    this.driver = driver ;
}
    public LandingPage addToCart(String productName){

        SeleniumUtils.click(driver, addToCartButton(productName));

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

        SeleniumUtils.click(driver, CART_ICON);

        return new CartPage(driver);
    }
    private By removeButton(String productName){
        return By.xpath(
                "//div[contains(text() ,'"+productName+"')]/ancestor::div[@class='inventory_item']//button"
        );
    }
    public LandingPage removeFromCart(String productName){

        SeleniumUtils.click(driver, removeButton(productName));

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

        SeleniumUtils.click(driver, menuButton);

        SeleniumUtils.click(driver, logoutButton);

        return new LoginPage(driver);
    }
}
