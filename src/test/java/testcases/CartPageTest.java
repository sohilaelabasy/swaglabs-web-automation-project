package testcases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LandingPage;
import pages.LoginPage;
import utilities.Constants;
import utilities.BaseTest;
import utilities.SeleniumUtils;

public class CartPageTest extends BaseTest {

    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @Test(groups = {"smoke"})
    public void cartShouldDisplayAddedProduct() {

        CartPage cartPage = addProductAndOpenCartPage();

        Assert.assertEquals(cartPage.getCartItemsCount(), 1);
        Assert.assertTrue(cartPage.isProductDisplayed(PRODUCT_NAME));
        System.out.println("Driver in CartPageTest = " + driver);
    }

    @Test
    public void removeProductShouldRemoveItemFromCart() {

        CartPage cartPage = addProductAndOpenCartPage();

        Assert.assertEquals(cartPage.getCartItemsCount(), 1);

        cartPage.removeProduct(PRODUCT_NAME);

        Assert.assertEquals(cartPage.getCartItemsCount(), 0);
    }

    @Test
    public void checkoutButtonShouldNavigateToCheckoutPage() {

        CartPage cartPage = addProductAndOpenCartPage();

        cartPage.clickCheckout();

        SeleniumUtils.wait(driver)
                .until(ExpectedConditions.urlToBe(
                        Constants.Links.CHECKOUT_STEP_ONE.getValue()
                ));

        Assert.assertEquals(
                driver.getCurrentUrl(),
                Constants.Links.CHECKOUT_STEP_ONE.getValue()
        );
    }

    private CartPage addProductAndOpenCartPage() {

        LoginPage loginPage = new LoginPage(driver);

        LandingPage landingPage = loginPage.login(
                Constants.Credentials.LOGIN_USERNAME.getValue(),
                Constants.Credentials.LOGIN_PASSWORD.getValue()
        );

        SeleniumUtils.wait(driver)
                .until(ExpectedConditions.urlToBe(
                        Constants.Links.LANDING_PAGE_URL.getValue()
                ));

        return landingPage
                .addToCart(PRODUCT_NAME)
                .clickCartIcon();
    }
    @Test
    public void removedProductShouldNotBeDisplayedInCart() {

        CartPage cartPage = addProductAndOpenCartPage();

        cartPage.removeProduct(PRODUCT_NAME);

        Assert.assertFalse(
                cartPage.isProductDisplayed(PRODUCT_NAME)
        );
    }
}