package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utilities.SeleniumUtils;

public class LoginPage {

    private WebDriver driver;

    private final By userName = By.id("user-name");
    private final By password = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.xpath("//h3[@data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUserName(String usernameText) {
        SeleniumUtils.type(driver, userName, usernameText);
    }

    public void enterPassword(String passwordText) {
        SeleniumUtils.type(driver, password, passwordText);
    }

    public void clickLogin() {
        SeleniumUtils.click(driver, loginButton);
    }

    public void login(String usernameText, String passwordText) {
        enterUserName(usernameText);
        enterPassword(passwordText);
        clickLogin();
    }

    public String getErrorMessage() {
        return SeleniumUtils.text(driver, errorMessage);
    }
}