package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utilities.Constants;
import utilities.SeleniumUtils;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;

    private final By userName = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.xpath("//h3[@data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage enterUserName(String usernameText) {
        SeleniumUtils.type(driver, userName, usernameText);
        return this ;
    }

    public LoginPage enterPassword(String passwordText) {
        SeleniumUtils.type(driver, password, passwordText);
        return this;
    }

    public LandingPage clickLogin() {
        SeleniumUtils.click(driver, loginButton);
        return new LandingPage(driver);
    }

    public LandingPage login(String usernameText, String passwordText) {
        SeleniumUtils.wait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(userName),
                ExpectedConditions.urlToBe(Constants.Links.LANDING_PAGE_URL.getValue())
        ));
        if (driver.getCurrentUrl().equals(Constants.Links.LANDING_PAGE_URL.getValue())) {
            return new LandingPage(driver);
        }

        enterUserName(usernameText);
        enterPassword(passwordText);

        return clickLogin();
    }

    public String getErrorMessage() {
        return SeleniumUtils.text(driver, errorMessage);
    }
}
