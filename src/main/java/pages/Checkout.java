package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.SeleniumUtils;

public class Checkout {
    private final WebDriver driver;

    private static final By FIRST_NAME = By.name("firstName");
    private static final By LAST_NAME = By.name("lastName");
    private static final By POSTAL_CODE = By.name("postalCode");
    private static final By CONTINUE_BUTTON = By.name("continue");

    public Checkout(WebDriver driver) {
        this.driver = driver;
    }

    public Checkout enterFirstName(String firstName) {
        SeleniumUtils.type(driver, FIRST_NAME, firstName);
        return this;
    }

    public Checkout enterLastName(String lastName) {
        SeleniumUtils.type(driver, LAST_NAME, lastName);
        return this;
    }

    public Checkout enterPostalCode(String postalCode) {
        SeleniumUtils.type(driver, POSTAL_CODE, postalCode);
        return this;
    }

    public Checkout clickContinueButton() {
        SeleniumUtils.click(driver, CONTINUE_BUTTON);
        return this;
    }

    public Checkout fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        return enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostalCode(postalCode);
    }
}
