package com.automation.helper;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ScreenshotHelper - Screenshot Capture Utility
 * Captures screenshots on test failure and saves them to the reports directory.
 */
public class ScreenshotHelper {

    private static final String SCREENSHOT_DIR = "reports/screenshots/";

    /**
     * Captures a screenshot and saves it with a timestamped filename.
     *
     * @param driver   the WebDriver instance
     * @param testName the name of the test (used in filename)
     * @return the path to the saved screenshot, or null if capture fails
     */
    public static String captureScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            System.err.println("Cannot capture screenshot: WebDriver is null");
            return null;
        }

        try {
            // Create screenshots directory if it doesn't exist
            Path screenshotDir = Paths.get(SCREENSHOT_DIR);
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            // Generate timestamped filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            Path destination = screenshotDir.resolve(fileName);

            // Capture and save screenshot
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Screenshot saved: " + destination.toAbsolutePath());
            return destination.toString();

        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
}
