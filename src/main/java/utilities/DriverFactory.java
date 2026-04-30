package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public final class DriverFactory {
    private static WebDriver driver;

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        if (DriverFactory.driver == null) {
            DriverFactory.driver = createChromeDriver();
        }
        return DriverFactory.driver;
    }

    public static void quitDriver() {
        if (driver == null) {
            return;
        }

        try {
            driver.quit();
        } catch (WebDriverException ignored) {
        } finally {
            driver = null;
        }
    }

    private static WebDriver createChromeDriver() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");

            WebDriver driver = new ChromeDriver(options);
            try {
                driver.manage().window().maximize();
            } catch (WebDriverException ignored) {
            }
            return driver;
        } catch (WebDriverException e) {
            throw new RuntimeException("Failed to initialize ChromeDriver", e);
        }
    }
}
