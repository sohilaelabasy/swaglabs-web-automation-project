package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class DriverFactory {
    private static WebDriver driver;

    private DriverFactory() {
    }

    public static WebDriver getDriver() {
        if (DriverFactory.driver == null) {
            DriverFactory.driver = createDriver(resolveBrowser());
        }
        return DriverFactory.driver;
    }

    public static WebDriver getDriver(String browserName) {
        if (DriverFactory.driver == null) {
            DriverFactory.driver = createDriver(browserName);
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

    private static String resolveBrowser() {
        String browser = System.getProperty("browser");
        if (browser != null && !browser.isBlank()) {
            return browser;
        }

        browser = System.getenv("BROWSER");
        if (browser != null && !browser.isBlank()) {
            return browser;
        }

        return "chrome";
    }

    private static WebDriver createDriver(String browserName) {
        String normalized = browserName == null ? "" : browserName.trim().toLowerCase();
        return switch (normalized) {
            case "edge", "msedge" -> createEdgeDriver();
            case "firefox", "ff" -> createFirefoxDriver();
            case "", "chrome" -> createChromeDriver();
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browserName + " (use chrome, edge, firefox)"
            );
        };
    }

    private static WebDriver createChromeDriver() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");

            WebDriver driver = new ChromeDriver(options);
            maximizeSafely(driver);
            return driver;
        } catch (WebDriverException e) {
            throw new RuntimeException("Failed to initialize ChromeDriver", e);
        }
    }

    private static WebDriver createEdgeDriver() {
        try {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");

            WebDriver driver = new EdgeDriver(options);
            maximizeSafely(driver);
            return driver;
        } catch (WebDriverException e) {
            throw new RuntimeException("Failed to initialize EdgeDriver", e);
        }
    }

    private static WebDriver createFirefoxDriver() {
        try {
            FirefoxOptions options = new FirefoxOptions();

            WebDriver driver = new FirefoxDriver(options);
            maximizeSafely(driver);
            return driver;
        } catch (WebDriverException e) {
            throw new RuntimeException("Failed to initialize FirefoxDriver", e);
        }
    }

    private static void maximizeSafely(WebDriver driver) {
        try {
            driver.manage().window().maximize();
        } catch (WebDriverException ignored) {
        }
    }
}
