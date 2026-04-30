package testcases;

import org.testng.annotations.Test;
import utilities.DriverFactory;

public class LoginTest extends BaseTest {
    @Test
    public void openLoginPage() {
        driver = DriverFactory.getDriver("chrome");
        driver.getCurrentUrl();
    }
}
