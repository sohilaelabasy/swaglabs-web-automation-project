package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Checkout;
import pages.CheckoutCompleted;
import pages.CheckoutStepTwo;
import pages.LandingPage;
import pages.LoginPage;
import utilities.Constants;
import utilities.SeleniumUtils;

public class CheckoutTest extends BaseTest {

    private static final String PRODUCT_NAME = "Sauce Labs Backpack";
    private static final By CHECKOUT_ERROR = By.cssSelector("[data-test='error']");

    @Test
    public void checkoutInformationShouldNavigateToStepTwo() {
        Checkout checkout = openCheckoutStepOneWithOneItem();
        checkout.fillCheckoutInformation(
                        Constants.CheckoutData.FIRST_NAME.getValue(),
                        Constants.CheckoutData.LAST_NAME.getValue(),
                        Constants.CheckoutData.POSTAL_CODE.getValue()
                )
                .clickContinueButton();

        CheckoutStepTwo checkoutStepTwo = new CheckoutStepTwo(driver);
        SeleniumUtils.wait(driver).until(ExpectedConditions.urlContains("checkout-step-two"));
        Assert.assertTrue(checkoutStepTwo.isCheckoutSummaryVisible());
    }

    @Test
    public void checkoutShouldShowErrorWhenFirstNameMissing() {
        Checkout checkout = openCheckoutStepOneWithOneItem();
        checkout.fillCheckoutInformation(
                        "",
                        Constants.CheckoutData.LAST_NAME.getValue(),
                        Constants.CheckoutData.POSTAL_CODE.getValue()
                )
                .clickContinueButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"));
        Assert.assertTrue(getCheckoutErrorText().contains("First Name is required"));
    }

    @Test
    public void checkoutShouldShowErrorWhenLastNameMissing() {
        Checkout checkout = openCheckoutStepOneWithOneItem();
        checkout.fillCheckoutInformation(
                        Constants.CheckoutData.FIRST_NAME.getValue(),
                        "",
                        Constants.CheckoutData.POSTAL_CODE.getValue()
                )
                .clickContinueButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"));
        Assert.assertTrue(getCheckoutErrorText().contains("Last Name is required"));
    }

    @Test
    public void checkoutShouldShowErrorWhenPostalCodeMissing() {
        Checkout checkout = openCheckoutStepOneWithOneItem();
        checkout.fillCheckoutInformation(
                        Constants.CheckoutData.FIRST_NAME.getValue(),
                        Constants.CheckoutData.LAST_NAME.getValue(),
                        ""
                )
                .clickContinueButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one"));
        Assert.assertTrue(getCheckoutErrorText().contains("Postal Code is required"));
    }

    @Test
    public void completeCheckoutShouldNavigateToCompletePage() {
        Checkout checkout = openCheckoutStepOneWithOneItem();
        checkout.fillCheckoutInformation(
                        Constants.CheckoutData.FIRST_NAME.getValue(),
                        Constants.CheckoutData.LAST_NAME.getValue(),
                        Constants.CheckoutData.POSTAL_CODE.getValue()
                )
                .clickContinueButton();

        CheckoutStepTwo checkoutStepTwo = new CheckoutStepTwo(driver);
        Assert.assertTrue(checkoutStepTwo.isCheckoutSummaryVisible());

        CheckoutCompleted completed = checkoutStepTwo.clickFinish();
        SeleniumUtils.wait(driver).until(ExpectedConditions.urlToBe(Constants.Links.COMPLETE_ORDER_PAGE_URL.getValue()));
        Assert.assertTrue(completed.isCheckoutCompleted());
    }

    private Checkout openCheckoutStepOneWithOneItem() {
        LoginPage loginPage = new LoginPage(driver);
        LandingPage landingPage = loginPage.login(
                Constants.Credentials.LOGIN_USERNAME.getValue(),
                Constants.Credentials.LOGIN_PASSWORD.getValue()
        );

        return landingPage
                .addToCart(PRODUCT_NAME)
                .clickCartIcon()
                .clickCheckout();
    }

    private String getCheckoutErrorText() {
        return SeleniumUtils.text(driver, CHECKOUT_ERROR);
    }
}
