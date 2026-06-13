package listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.io.File;
import java.io.IOException;

public class AllureReportListener implements ISuiteListener {

    private static final Logger logger = LoggerFactory.getLogger(AllureReportListener.class);

    @Override
    public void onFinish(ISuite suite) {
        String resultsPath = System.getProperty("user.dir") + File.separator + "allure-results";
        logger.info("Opening Allure report...");
        try {
            new ProcessBuilder("cmd", "/c", "allure", "serve", resultsPath)
                    .inheritIO()
                    .start();
        } catch (IOException e) {
            logger.error("Could not open Allure report. Ensure Allure CLI is installed.", e);
        }
    }
}
