package com.automation.helper;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WaitHelper - Explicit and Fluent Wait Utilities
 * Provides reusable wait methods to handle dynamic web elements.
 */
public class WaitHelper {

    private final WebDriver driver;
    private final int defaultTimeout;

    public WaitHelper(WebDriver driver, int timeoutInSeconds) {
        this.driver = driver;
        this.defaultTimeout = timeoutInSeconds;
    }

    public WaitHelper(WebDriver driver) {
        this(driver, 15);
    }

    /**
     * Waits until the element is visible on the page.
     */
    public WebElement waitForElementVisible(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until the element is visible with a custom timeout.
     */
    public WebElement waitForElementVisible(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits until the element is clickable.
     */
    public WebElement waitForElementClickable(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until the element is clickable with a custom timeout.
     */
    public WebElement waitForElementClickable(By locator, int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Waits until the element is present in the DOM (may not be visible).
     */
    public WebElement waitForElementPresent(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Waits until the element is no longer visible.
     */
    public boolean waitForElementInvisible(By locator) {
        return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout))
                .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Waits for page title to contain the specified text.
     */
    public boolean waitForTitleContains(String titleFragment) {
        return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout))
                .until(ExpectedConditions.titleContains(titleFragment));
    }

    /**
     * Waits for URL to contain the specified text.
     */
    public boolean waitForUrlContains(String urlFragment) {
        return new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout))
                .until(ExpectedConditions.urlContains(urlFragment));
    }

    /**
     * Fluent wait with configurable polling interval.
     */
    public WebElement fluentWait(By locator, int timeoutSeconds, int pollingMillis) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollingMillis))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
