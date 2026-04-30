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

   

   
}
