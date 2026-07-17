package com.automation.base;

import com.automation.helper.ScreenshotHelper;
import com.automation.reporting.ExtentReportManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/**
 * BaseTest - Test Lifecycle Manager
 * Provides setup and teardown methods for all test classes.
 * Manages WebDriver initialization, configuration loading, and reporting.
 */
public class BaseTest {

    protected WebDriver driver;
    protected Properties config;
    protected ExtentTest extentTest;

    /**
     * Loads configuration from config.properties before the test suite.
     */
    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        config = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/testdata/config.properties");
            config.load(fis);
            fis.close();
        } catch (IOException e) {
            System.err.println("WARNING: Could not load config.properties. Using defaults.");
            config.setProperty("browser", "chrome");
            config.setProperty("headless", "true");
            config.setProperty("base.url", "https://www.google.com");
            config.setProperty("implicit.wait", "10");
            config.setProperty("explicit.wait", "15");
        }
    }

    /**
     * Initializes WebDriver before each test method.
     * Reads browser and headless settings from config or system properties.
     */
    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestResult result) {
        // Allow system properties to override config (for Jenkins parameterized builds)
        String browser = System.getProperty("browser", config.getProperty("browser", "chrome"));
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", config.getProperty("headless", "true"))
        );

        driver = DriverManager.initDriver(browser, headless);

        // Set implicit wait
        int implicitWait = Integer.parseInt(config.getProperty("implicit.wait", "10"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        // Create ExtentTest for this test method
        String testName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription();
        extentTest = ExtentReportManager.createTest(testName,
                testDescription != null ? testDescription : "");
        extentTest.log(Status.INFO, "Browser: " + browser + " | Headless: " + headless);
    }

    /**
     * Captures screenshot on failure and logs result to ExtentReports.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            extentTest.log(Status.FAIL, "Test FAILED: " + result.getThrowable().getMessage());

            // Capture screenshot on failure
            String screenshotPath = ScreenshotHelper.captureScreenshot(
                    driver, result.getMethod().getMethodName());
            if (screenshotPath != null) {
                extentTest.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
            }
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, "Test SKIPPED: " + result.getThrowable().getMessage());
        } else {
            extentTest.log(Status.PASS, "Test PASSED");
        }

        // Quit driver
        DriverManager.quitDriver();
    }

    /**
     * Flushes the ExtentReports after the suite completes.
     */
    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        ExtentReportManager.flushReport();
    }

    /**
     * Navigates to the base URL configured in config.properties.
     */
    protected void navigateToBaseUrl() {
        String baseUrl = config.getProperty("base.url", "https://www.google.com");
        driver.get(baseUrl);
        extentTest.log(Status.INFO, "Navigated to: " + baseUrl);
    }
}
