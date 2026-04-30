package utilities;

public enum Constants {
    LOGIN_USERNAME("standard_user"),
    LOGIN_PASSWORD("secret_sauce"),
    LOGIN_BASE_URL("https://www.saucedemo.com/"),

    LANDING_PAGE_URL("https://www.saucedemo.com/inventory.html"),
    CART_PAGE_URL("https://www.saucedemo.com/cart.html"),
    CHECKOUT_STEP_ONE("https://www.saucedemo.com/checkout-step-one.html"),
    COMPLETE_ORDER_PAGE_URL("https://www.saucedemo.com/checkout-complete.html"),

    CHECKOUT_FIRST_NAME("Mohamed"),
    CHECKOUT_LAST_NAME("Ahmed"),
    CHECKOUT_POSTAL_CODE("32951"),

    PRODUCT_ITEM_1("SauceLabsBackpack"),
    PRODUCT_ITEM_2("SauceLabsBikeLight"),
    PRODUCT_ITEM_3("SauceLabsBoltTShirt"),
    PRODUCT_ITEM_4("SauceLabsFleeceJacket"),
    PRODUCT_ITEM_5("SauceLabsOnesie"),
    PRODUCT_ITEM_6("TShirtRed"),

    PRODUCT_ITEM_1_REMOVE("SauceLabsBackpack_remove"),
    PRODUCT_ITEM_2_REMOVE("SauceLabsBikeLight_remove"),
    PRODUCT_ITEM_3_REMOVE("SauceLabsBoltTShirt_remove"),
    PRODUCT_ITEM_4_REMOVE("SauceLabsFleeceJacket_remove"),
    PRODUCT_ITEM_5_REMOVE("SauceLabsOnesie_remove"),
    PRODUCT_ITEM_6_REMOVE("TShirtRed_remove");

    private final String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
