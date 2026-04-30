package testcases;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;
import utilities.Constants;
import utilities.DriverFactory;

public class BaseTest {

    protected WebDriver driver;

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp() {
        logger.info("Opening browser");
        driver = DriverFactory.getDriver();

        logger.info("Navigating to SauceDemo");
        driver.get(Constants.Links.LOGIN_BASE_URL.getValue());
    }

    @AfterMethod
    public void tearDown() {
        logger.info("Closing browser");
        DriverFactory.quitDriver();
    }
}
