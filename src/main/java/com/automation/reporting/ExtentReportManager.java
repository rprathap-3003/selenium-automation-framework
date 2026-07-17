package com.automation.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportManager - Singleton Report Manager
 * Manages ExtentReports lifecycle including initialization, test logging, and report generation.
 * Implements ITestListener for automatic TestNG integration.
 */
public class ExtentReportManager implements ITestListener {

    private static ExtentReports extent;
    private static final String REPORT_DIR = "reports/";
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    /**
     * Initializes ExtentReports with a Spark reporter.
     * Uses singleton pattern to ensure only one instance exists.
     */
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            // Create report directory
            new File(REPORT_DIR).mkdirs();

            // Generate timestamped report filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = REPORT_DIR + "ExtentReport_" + timestamp + ".html";

            // Configure Spark Reporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("Selenium Automation Framework - Test Results");
            sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

            // Initialize ExtentReports
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("Browser", System.getProperty("browser", "chrome"));
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    /**
     * Creates a new test entry in the report.
     */
    public static ExtentTest createTest(String testName, String description) {
        ExtentTest test = getInstance().createTest(testName, description);
        testThreadLocal.set(test);
        return test;
    }

    /**
     * Returns the current thread's ExtentTest instance.
     */
    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }

    /**
     * Flushes all report data to the HTML file.
     */
    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    // ===== ITestListener Implementation =====

    @Override
    public void onStart(ITestContext context) {
        System.out.println("=== Test Suite Started: " + context.getName() + " ===");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("=== Test Suite Finished: " + context.getName() + " ===");
        flushReport();
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(">>> Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✓ Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("✗ Test Failed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("⊘ Test Skipped: " + result.getMethod().getMethodName());
    }
}
