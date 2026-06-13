package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.Constants;
import utilities.SeleniumUtils;

import java.time.Duration;

import java.util.List;

public class CheckoutStepTwo {
    private final WebDriver driver;

    private static final By CHECKOUT_SUMMARY_CONTAINER = By.id("checkout_summary_container");
    private static final By CART_ITEMS = By.cssSelector("[data-test='inventory-item']");
    private static final By ITEM_NAME = By.cssSelector("[data-test='inventory-item-name']");
    private static final By ITEM_DESCRIPTION = By.cssSelector("[data-test='inventory-item-desc']");
    private static final By ITEM_PRICE = By.cssSelector("[data-test='inventory-item-price']");
    private static final By ITEM_QUANTITY = By.cssSelector("[data-test='item-quantity']");

    private static final By PAYMENT_INFO_VALUE = By.cssSelector("[data-test='payment-info-value']");
    private static final By SHIPPING_INFO_VALUE = By.cssSelector("[data-test='shipping-info-value']");

    private static final By SUBTOTAL_LABEL = By.cssSelector("[data-test='subtotal-label']");
    private static final By TAX_LABEL = By.cssSelector("[data-test='tax-label']");
    private static final By TOTAL_LABEL = By.cssSelector("[data-test='total-label']");

    private static final By CANCEL_BUTTON = By.id("cancel");
    private static final By FINISH_BUTTON = By.id("finish");

    public CheckoutStepTwo(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isCheckoutSummaryVisible() {
        try {
            return SeleniumUtils.waitVisible(driver, CHECKOUT_SUMMARY_CONTAINER).isDisplayed();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public int getItemsCount() {
        SeleniumUtils.waitVisible(driver, CHECKOUT_SUMMARY_CONTAINER);
        return driver.findElements(CART_ITEMS).size();
    }

    public List<String> getItemNames() {
        SeleniumUtils.waitVisible(driver, CHECKOUT_SUMMARY_CONTAINER);
        return driver.findElements(ITEM_NAME).stream().map(WebElement::getText).toList();
    }

    public List<String> getItemDescriptions() {
        SeleniumUtils.waitVisible(driver, CHECKOUT_SUMMARY_CONTAINER);
        return driver.findElements(ITEM_DESCRIPTION).stream().map(WebElement::getText).toList();
    }

    public List<String> getItemPricesText() {
        SeleniumUtils.waitVisible(driver, CHECKOUT_SUMMARY_CONTAINER);
        return driver.findElements(ITEM_PRICE).stream().map(WebElement::getText).toList();
    }

    public List<Double> getItemPrices() {
        return getItemPricesText().stream().map(this::parseCurrency).toList();
    }

    public List<Integer> getItemQuantities() {
        SeleniumUtils.waitVisible(driver, CHECKOUT_SUMMARY_CONTAINER);
        return driver.findElements(ITEM_QUANTITY).stream()
                .map(WebElement::getText)
                .map(text -> text == null || text.isBlank() ? "0" : text.trim())
                .map(Integer::parseInt)
                .toList();
    }

    public String getPaymentInfo() {
        return SeleniumUtils.text(driver, PAYMENT_INFO_VALUE);
    }

    public String getShippingInfo() {
        return SeleniumUtils.text(driver, SHIPPING_INFO_VALUE);
    }

    public String getSubtotalText() {
        return SeleniumUtils.text(driver, SUBTOTAL_LABEL);
    }

    public double getSubtotal() {
        return parseCurrency(getSubtotalText());
    }

    public String getTaxText() {
        return SeleniumUtils.text(driver, TAX_LABEL);
    }

    public double getTax() {
        return parseCurrency(getTaxText());
    }

    public String getTotalText() {
        return SeleniumUtils.text(driver, TOTAL_LABEL);
    }

    public double getTotal() {
        return parseCurrency(getTotalText());
    }

    public boolean isTotalCalculatedCorrectly(double tolerance) {
        double itemsTotal = getItemPrices().stream().mapToDouble(Double::doubleValue).sum();
        double expectedSubtotal = round2(itemsTotal);
        double expectedTotal = round2(expectedSubtotal + getTax());
        return Math.abs(expectedSubtotal - getSubtotal()) <= tolerance
                && Math.abs(expectedTotal - getTotal()) <= tolerance;
    }

    public LandingPage clickCancel() {
        SeleniumUtils.click(driver, CANCEL_BUTTON);
        return new LandingPage(driver);
    }

    public CheckoutCompleted clickFinish() {
        WebElement button = SeleniumUtils.waitClickable(driver, FINISH_BUTTON, Duration.ofSeconds(10));
        button.click();
        try {
            SeleniumUtils.wait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.urlToBe(Constants.Links.COMPLETE_ORDER_PAGE_URL.getValue()));
            return new CheckoutCompleted(driver);
        } catch (RuntimeException ignored) {
            // Click didn't trigger navigation — try JS click
        }
        WebElement retry = SeleniumUtils.waitClickable(driver, FINISH_BUTTON, Duration.ofSeconds(5));
        if (driver instanceof JavascriptExecutor js) {
            js.executeScript("arguments[0].click();", retry);
        } else {
            retry.click();
        }
        return new CheckoutCompleted(driver);
    }

    private double parseCurrency(String text) {
        if (text == null || text.isBlank()) {
            return 0.0;
        }
        String normalized = text.replaceAll("[^0-9.\\-]", "");
        if (normalized.isBlank() || normalized.equals("-") || normalized.equals(".")) {
            return 0.0;
        }
        return Double.parseDouble(normalized);
    }

    private double round2(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
