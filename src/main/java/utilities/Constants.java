package utilities;
import java.lang.String;

// i used Enums as it's better for calling in code
public enum Constants {
    LOGIN_USERNAME("standard_user"),
    LOGIN_PASSWORD("secret_sauce"),
    LOGIN_BASE_URL("https://www.saucedemo.com/"),
    LANDING_PAGE_URL("https://www.saucedemo.com/inventory.html"),
    CART_PAGE_URL("https://www.saucedemo.com/cart.html"),
    COMPLETE_ORDER_PAGE_URL("https://www.saucedemo.com/checkout-complete.html");

    public final String value;
    Constants(String value) {
        this.value = value;
    }
}
