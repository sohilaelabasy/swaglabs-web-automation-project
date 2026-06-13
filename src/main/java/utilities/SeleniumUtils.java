package utilities;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public final class SeleniumUtils {
    private static final Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);

    private SeleniumUtils() {
    }

    public static WebDriverWait wait(WebDriver driver) {
        return wait(driver, DEFAULT_TIMEOUT);
    }

    public static WebDriverWait wait(WebDriver driver, Duration timeout) {
        return new WebDriverWait(driver, timeout == null ? DEFAULT_TIMEOUT : timeout);
    }

    public static WebElement find(WebDriver driver, By locator) {
        return driver.findElement(locator);
    }

    public static WebElement waitVisible(WebDriver driver, By locator) {
        return waitVisible(driver, locator, DEFAULT_TIMEOUT);
    }

    public static WebElement waitVisible(WebDriver driver, By locator, Duration timeout) {
        return wait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitClickable(WebDriver driver, By locator) {
        return waitClickable(driver, locator, DEFAULT_TIMEOUT);
    }

    public static WebElement waitClickable(WebDriver driver, By locator, Duration timeout) {
        return wait(driver, timeout).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void click(WebDriver driver, By locator) {
        click(driver, locator, DEFAULT_TIMEOUT);
    }

    public static void click(WebDriver driver, By locator, Duration timeout) {
        waitClickable(driver, locator, timeout).click();
    }



    public static void type(WebDriver driver, By locator, String text) {
        WebElement element = waitVisible(driver, locator);
        element.clear();
        element.sendKeys(text);
    }

    public static String text(WebDriver driver, By locator) {
        return text(driver, locator, DEFAULT_TIMEOUT);
    }

    public static String text(WebDriver driver, By locator, Duration timeout) {
        return waitVisible(driver, locator, timeout).getText();
    }

    public static String attribute(WebDriver driver, By locator, String attributeName) {
        return waitVisible(driver, locator, DEFAULT_TIMEOUT).getAttribute(attributeName);
    }

    public static void scrollIntoView(WebDriver driver, By locator) {
        WebElement element = find(driver, locator);
        if (driver instanceof JavascriptExecutor js) {
            js.executeScript("arguments[0].scrollIntoView({block:'center', inline:'nearest'});", element);
        }
    }

    public static void selectByVisibleText(WebDriver driver, By locator, String visibleText) {
        selectByVisibleText(driver, locator, visibleText, DEFAULT_TIMEOUT);
    }

    public static void selectByVisibleText(WebDriver driver, By locator, String visibleText, Duration timeout) {
        WebElement element = waitVisible(driver, locator, timeout);
        new Select(element).selectByVisibleText(visibleText);
    }

    public static byte[] screenshotBytes(WebDriver driver) {
        try {
            if (driver instanceof TakesScreenshot takesScreenshot) {
                return takesScreenshot.getScreenshotAs(OutputType.BYTES);
            }
            return new byte[0];
        } catch (WebDriverException e) {
            logger.warn("Failed to capture screenshot", e);
            return new byte[0];
        }
    }

    public static void attachScreenshotToAllure(WebDriver driver, String name) {
        byte[] bytes = screenshotBytes(driver);
        if (bytes.length == 0) {
            return;
        }
        Allure.addAttachment(name == null || name.isBlank() ? "screenshot" : name, new ByteArrayInputStream(bytes));
    }
}
