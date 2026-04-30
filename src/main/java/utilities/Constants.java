package utilities;

public final class Constants {
    private Constants() {
    }

    public enum Credentials {
        LOGIN_USERNAME("standard_user"),
        LOGIN_PASSWORD("secret_sauce");

        private final String value;

        Credentials(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    public enum Locators{
        LOGIN_ID("user-name"),
        LOGIN_PASSWORD_ID("password"),
        LOGIN_BUTTON_ID("login-button");

       private final String id;

        Locators(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }  
    }

    public enum Links {
        LOGIN_BASE_URL("https://www.saucedemo.com/"),
        LANDING_PAGE_URL("https://www.saucedemo.com/inventory.html"),
        CART_PAGE_URL("https://www.saucedemo.com/cart.html"),
        CHECKOUT_STEP_ONE("https://www.saucedemo.com/checkout-step-one.html"),
        COMPLETE_ORDER_PAGE_URL("https://www.saucedemo.com/checkout-complete.html");

        private final String value;

        Links(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum CheckoutData {
        FIRST_NAME("Mohamed"),
        LAST_NAME("Ahmed"),
        POSTAL_CODE("32951");

        private final String value;

        CheckoutData(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ProductItems {
        ITEM_1("add-to-cart-sauce-labs-backpack"),
        ITEM_2("add-to-cart-sauce-labs-bike-light"),
        ITEM_3("add-to-cart-sauce-labs-bolt-t-shirt"),
        ITEM_4("add-to-cart-sauce-labs-fleece-jacket"),
        ITEM_5("add-to-cart-sauce-labs-onesie"),
        ITEM_6("add-to-cart-test.allthethings()-t-shirt-(red)");

        private final String value;

        ProductItems(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public enum ProductRemoveIds {
        ITEM_1_REMOVE("remove-sauce-labs-backpack"),
        ITEM_2_REMOVE("remove-sauce-labs-bike-light"),
        ITEM_3_REMOVE("remove-sauce-labs-bolt-t-shirt"),
        ITEM_4_REMOVE("remove-sauce-labs-fleece-jacket"),
        ITEM_5_REMOVE("remove-sauce-labs-onesie"),
        ITEM_6_REMOVE("remove-test.allthethings()-t-shirt-(red)");

        private final String value;

        ProductRemoveIds(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
