package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.Constants;
import utilities.SeleniumUtils;

import java.time.Duration;

public class Checkout {
    private final WebDriver driver;

    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By CHECKOUT_ERROR = By.cssSelector("[data-test='error']");

    public Checkout(WebDriver driver) {
        this.driver = driver;
    }

    public Checkout fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        SeleniumUtils.wait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlToBe(Constants.Links.CHECKOUT_STEP_ONE.getValue()));
        SeleniumUtils.waitVisible(driver, FIRST_NAME, Duration.ofSeconds(10));
        typeField(FIRST_NAME, firstName);
        typeField(LAST_NAME, lastName);
        typeField(POSTAL_CODE, postalCode);
        return this;
    }

    public Checkout clickContinueButton() {
        WebElement button = SeleniumUtils.waitClickable(driver, CONTINUE_BUTTON, Duration.ofSeconds(10));
        SeleniumUtils.scrollIntoView(driver, CONTINUE_BUTTON);
        try {
            button.click();
        } catch (RuntimeException e) {
            submitFormViaJs(button);
            return this;
        }
        if (!waitForFormReaction()) {
            submitFormViaJs(button);
        }
        return this;
    }

    private boolean waitForFormReaction() {
        try {
            SeleniumUtils.wait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("checkout-step-two"),
                    ExpectedConditions.visibilityOfElementLocated(CHECKOUT_ERROR)
            ));
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void submitFormViaJs(WebElement button) {
        if (driver instanceof JavascriptExecutor js) {
            js.executeScript(
                    "const btn = arguments[0]; if (btn.form) btn.form.requestSubmit(btn); else btn.click();",
                    button
            );
        }
    }

    private void typeField(By locator, String value) {
        WebElement field = SeleniumUtils.waitVisible(driver, locator, Duration.ofSeconds(10));
        // sendKeys updates the DOM but not React's internal state — use native value setter
        // so React's document-level input listener sees the change and calls onChange.
        if (driver instanceof JavascriptExecutor js) {
            String v = value != null ? value : "";
            js.executeScript(
                "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                "setter.call(arguments[0], arguments[1]);" +
                "arguments[0].dispatchEvent(new Event('input', {bubbles:true}));",
                field, v
            );
        } else {
            field.click();
            field.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            if (value != null && !value.isEmpty()) {
                field.sendKeys(value);
            } else {
                field.sendKeys(Keys.DELETE);
            }
        }
    }
}
