package com.automation.helper;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * ElementHelper - Common Element Interaction Utilities
 * Provides simplified methods for interacting with web elements.
 */
public class ElementHelper {

    private final WebDriver driver;
    private final WaitHelper waitHelper;

    public ElementHelper(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    public ElementHelper(WebDriver driver, int waitTimeout) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver, waitTimeout);
    }

    /**
     * Clicks on an element after waiting for it to be clickable.
     */
    public void click(By locator) {
        waitHelper.waitForElementClickable(locator).click();
    }

    /**
     * Types text into an element after clearing it.
     */
    public void type(By locator, String text) {
        WebElement element = waitHelper.waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Returns the visible text of an element.
     */
    public String getText(By locator) {
        return waitHelper.waitForElementVisible(locator).getText();
    }

    /**
     * Returns the value of the specified attribute.
     */
    public String getAttribute(By locator, String attribute) {
        return waitHelper.waitForElementPresent(locator).getAttribute(attribute);
    }

    /**
     * Checks if an element is displayed on the page.
     */
    public boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Checks if an element is enabled.
     */
    public boolean isEnabled(By locator) {
        try {
            return driver.findElement(locator).isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Selects a dropdown option by visible text.
     */
    public void selectByVisibleText(By locator, String text) {
        Select select = new Select(waitHelper.waitForElementPresent(locator));
        select.selectByVisibleText(text);
    }

    /**
     * Selects a dropdown option by value.
     */
    public void selectByValue(By locator, String value) {
        Select select = new Select(waitHelper.waitForElementPresent(locator));
        select.selectByValue(value);
    }

    /**
     * Selects a dropdown option by index.
     */
    public void selectByIndex(By locator, int index) {
        Select select = new Select(waitHelper.waitForElementPresent(locator));
        select.selectByIndex(index);
    }

    /**
     * Scrolls to the specified element.
     */
    public void scrollToElement(By locator) {
        WebElement element = waitHelper.waitForElementPresent(locator);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
    }

    /**
     * Performs a hover action on the specified element.
     */
    public void hoverOver(By locator) {
        WebElement element = waitHelper.waitForElementVisible(locator);
        new Actions(driver).moveToElement(element).perform();
    }

    /**
     * Double-clicks on the specified element.
     */
    public void doubleClick(By locator) {
        WebElement element = waitHelper.waitForElementClickable(locator);
        new Actions(driver).doubleClick(element).perform();
    }

    /**
     * Right-clicks on the specified element.
     */
    public void rightClick(By locator) {
        WebElement element = waitHelper.waitForElementClickable(locator);
        new Actions(driver).contextClick(element).perform();
    }

    /**
     * Returns a list of elements matching the locator.
     */
    public List<WebElement> getElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Clicks an element using JavaScript (useful for hidden elements).
     */
    public void jsClick(By locator) {
        WebElement element = waitHelper.waitForElementPresent(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
