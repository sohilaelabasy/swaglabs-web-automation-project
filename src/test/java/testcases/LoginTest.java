package testcases;

import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    public void openLoginPage() {
        driver.getCurrentUrl();
    }
}
