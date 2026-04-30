package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DriverFactory {

    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    private static WebDriver driver;

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = resolveBrowser();
            logger.info("No driver found. Creating new driver for: {}", browser);
            driver = createDriver(browser);
        }
        return driver;
    }

    public static WebDriver getDriver(String browserName) {
        if (driver == null) {
            logger.info("Creating driver for browser: {}", browserName);
            driver = createDriver(browserName);
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver == null) {
            logger.warn("Attempted to quit driver but driver is null");
            return;
        }

        try {
            logger.info("Closing browser");
            driver.quit();
        } catch (WebDriverException e) {
            logger.error("Error while quitting driver", e);
        } finally {
            driver = null;
        }
    }

    private static String resolveBrowser() {
        String browser = System.getProperty("browser");

        if (browser != null && !browser.isBlank()) {
            logger.info("Using browser from system property: {}", browser);
            return browser;
        }

        browser = System.getenv("BROWSER");

        if (browser != null && !browser.isBlank()) {
            logger.info("Using browser from environment variable: {}", browser);
            return browser;
        }

        logger.warn("No browser specified. Defaulting to Chrome");
        return "chrome";
    }

    private static WebDriver createDriver(String browserName) {
        String normalized = browserName == null ? "" : browserName.trim().toLowerCase();

        logger.info("Initializing driver for: {}", normalized);

        return switch (normalized) {
            case "edge", "msedge" -> createEdgeDriver();
            case "firefox", "ff" -> createFirefoxDriver();
            case "", "chrome" -> createChromeDriver();
            default -> {
                logger.error("Unsupported browser: {}", browserName);
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browserName + " (use chrome, edge, firefox)"
                );
            }
        };
    }

    private static WebDriver createChromeDriver() {
        try {
            logger.info("Starting ChromeDriver");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");

            WebDriver driver = new ChromeDriver(options);
            maximizeSafely(driver);

            return driver;
        } catch (WebDriverException e) {
            logger.error("Failed to initialize ChromeDriver", e);
            throw new RuntimeException("Failed to initialize ChromeDriver", e);
        }
    }

    private static WebDriver createEdgeDriver() {
        try {
            logger.info("Starting EdgeDriver");

            EdgeOptions options = new EdgeOptions();
            options.addArguments("--start-maximized");

            WebDriver driver = new EdgeDriver(options);
            maximizeSafely(driver);

            return driver;
        } catch (WebDriverException e) {
            logger.error("Failed to initialize EdgeDriver", e);
            throw new RuntimeException("Failed to initialize EdgeDriver", e);
        }
    }

    private static WebDriver createFirefoxDriver() {
        try {
            logger.info("Starting FirefoxDriver");

            FirefoxOptions options = new FirefoxOptions();

            WebDriver driver = new FirefoxDriver(options);
            maximizeSafely(driver);

            return driver;
        } catch (WebDriverException e) {
            logger.error("Failed to initialize FirefoxDriver", e);
            throw new RuntimeException("Failed to initialize FirefoxDriver", e);
        }
    }

    private static void maximizeSafely(WebDriver driver) {
        try {
            driver.manage().window().maximize();
        } catch (WebDriverException e) {
            logger.warn("Could not maximize browser window");
        }
    }
}