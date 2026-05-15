package listeners;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.SeleniumUtils;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class ITest implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(ITest.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("TEST START: {}", testId(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("TEST PASS: {}", testId(result));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("TEST FAIL: {}", testId(result), result.getThrowable());
        WebDriver driver = resolveDriver(result);
        if (driver != null) {
            SeleniumUtils.attachScreenshotToAllure(driver, testId(result));
            attachText("URL", safeGetCurrentUrl(driver));
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("TEST SKIP: {}", testId(result));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        logger.warn("TEST FAIL (within success %): {}", testId(result));
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    @Override
    public void onStart(ITestContext context) {
        logger.info("CONTEXT START: {} ({} tests)", context.getName(), context.getAllTestMethods().length);
        String methods = java.util.Arrays.stream(context.getAllTestMethods())
                .map(m -> m.getRealClass().getSimpleName() + "." + m.getMethodName())
                .collect(Collectors.joining(", "));
        if (!methods.isBlank()) {
            logger.info("CONTEXT TESTS: {}", methods);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("CONTEXT FINISH: {}", context.getName());
    }

    private String testId(ITestResult result) {
        if (result == null) {
            return "unknown";
        }
        if (result.getMethod() == null) {
            return String.valueOf(result.getName());
        }
        String className = result.getMethod().getRealClass() == null
                ? "UnknownClass"
                : result.getMethod().getRealClass().getSimpleName();
        return className + "." + result.getMethod().getMethodName();
    }

    private WebDriver resolveDriver(ITestResult result) {
        if (result == null) {
            return null;
        }
        Object instance = result.getInstance();
        if (instance == null) {
            return null;
        }
        Field field = findField(instance.getClass(), "driver");
        if (field == null) {
            return null;
        }
        try {
            field.setAccessible(true);
            Object value = field.get(instance);
            return value instanceof WebDriver webDriver ? webDriver : null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }

    private Field findField(Class<?> type, String fieldName) {
        Class<?> current = type;
        while (current != null && current != Object.class) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ignored) {
                current = current.getSuperclass();
            }
        }
        return null;
    }

    private void attachText(String name, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        Allure.addAttachment(name, value);
    }

    private String safeGetCurrentUrl(WebDriver driver) {
        try {
            return driver.getCurrentUrl();
        } catch (RuntimeException e) {
            return "";
        }
    }
}
