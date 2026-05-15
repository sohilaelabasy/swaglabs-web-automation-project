package testcases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LandingPage;
import pages.LoginPage;
import utilities.Constants;
import utilities.SeleniumUtils;

public class LandingPageTest extends BaseTest {

    private static final String PRODUCT_NAME = "Sauce Labs Backpack";

    @Test
    public void addToCartShouldIncreaseCartBadgeCount() {
        LandingPage landingPage = loginAndOpenLandingPage();
        Assert.assertEquals(landingPage.getCartBadgeCount(), 0);

        landingPage.addToCart(PRODUCT_NAME);

        Assert.assertEquals(landingPage.getCartBadgeCount(), 1);
    }

    @Test
    public void removeFromCartShouldDecreaseCartBadgeCount() {
        LandingPage landingPage = loginAndOpenLandingPage();
        landingPage.addToCart(PRODUCT_NAME);
        Assert.assertEquals(landingPage.getCartBadgeCount(), 1);

        landingPage.removeFromCart(PRODUCT_NAME);

        Assert.assertEquals(landingPage.getCartBadgeCount(), 0);
    }

    @Test
    public void cartIconShouldOpenCartPageWithSelectedItem() {
        LandingPage landingPage = loginAndOpenLandingPage();
        landingPage.addToCart(PRODUCT_NAME);

        CartPage cartPage = landingPage.clickCartIcon();
        SeleniumUtils.wait(driver).until(ExpectedConditions.urlContains("cart"));

        Assert.assertEquals(cartPage.getCartItemsCount(), 1);
        Assert.assertTrue(cartPage.isProductDisplayed(PRODUCT_NAME));
    }

    @Test
    public void logoutShouldReturnToLoginPage() {
        LandingPage landingPage = loginAndOpenLandingPage();
        LoginPage loginPage = landingPage.clickLogout();

        Assert.assertNotNull(loginPage);
        SeleniumUtils.wait(driver).until(ExpectedConditions.urlToBe(Constants.Links.LOGIN_BASE_URL.getValue()));
        Assert.assertEquals(driver.getCurrentUrl(), Constants.Links.LOGIN_BASE_URL.getValue());
    }

    private LandingPage loginAndOpenLandingPage() {
        LoginPage loginPage = new LoginPage(driver);
        LandingPage landingPage = loginPage.login(
                Constants.Credentials.LOGIN_USERNAME.getValue(),
                Constants.Credentials.LOGIN_PASSWORD.getValue()
        );
        SeleniumUtils.wait(driver).until(ExpectedConditions.urlToBe(Constants.Links.LANDING_PAGE_URL.getValue()));
        return landingPage;
    }
}
