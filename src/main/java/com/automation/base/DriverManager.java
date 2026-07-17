package com.automation.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * DriverManager - WebDriver Factory
 * Manages browser driver instantiation with support for Chrome, Firefox, and Edge.
 * Supports headless mode for CI/CD pipeline execution.
 * Uses ThreadLocal for parallel test execution safety.
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Initializes and returns a WebDriver instance based on the specified browser.
     *
     * @param browser  the browser name (chrome, firefox, edge)
     * @param headless whether to run in headless mode
     * @return WebDriver instance
     */
    public static WebDriver initDriver(String browser, boolean headless) {
        WebDriver driver;

        switch (browser.toLowerCase().trim()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless");
                }
                firefoxOptions.addArguments("--window-size=1920,1080");
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless");
                }
                edgeOptions.addArguments("--window-size=1920,1080");
                edgeOptions.addArguments("--disable-gpu");
                driver = new EdgeDriver(edgeOptions);
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--window-size=1920,1080");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                driver = new ChromeDriver(chromeOptions);
                break;
        }

        driver.manage().window().maximize();
        driverThreadLocal.set(driver);
        return driver;
    }

    /**
     * Returns the current thread's WebDriver instance.
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Quits the current thread's WebDriver and removes it from ThreadLocal.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }
}
