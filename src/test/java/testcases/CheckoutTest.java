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
    private static final String FIRST_NAME_REQUIRED_ERROR = "Error: First Name is required";
    private static final String LAST_NAME_REQUIRED_ERROR = "Error: Last Name is required";
    private static final String POSTAL_CODE_REQUIRED_ERROR = "Error: Postal Code is required";
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

        assertCheckoutStepOneError(FIRST_NAME_REQUIRED_ERROR);
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

        assertCheckoutStepOneError(LAST_NAME_REQUIRED_ERROR);
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

        assertCheckoutStepOneError(POSTAL_CODE_REQUIRED_ERROR);
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
        SeleniumUtils.wait(driver).until(ExpectedConditions.urlContains("checkout-step-two"));

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

        SeleniumUtils.wait(driver).until(ExpectedConditions.urlToBe(Constants.Links.LANDING_PAGE_URL.getValue()));

        return landingPage
                .addToCart(PRODUCT_NAME)
                .clickCartIcon()
                .clickCheckout();
    }

    private void assertCheckoutStepOneError(String expectedMessage) {
        SeleniumUtils.wait(driver).until(ExpectedConditions.urlToBe(Constants.Links.CHECKOUT_STEP_ONE.getValue()));
        Assert.assertEquals(getCheckoutErrorText(), expectedMessage);
    }

    private String getCheckoutErrorText() {
        return SeleniumUtils.text(driver, CHECKOUT_ERROR).trim();
    }
}
