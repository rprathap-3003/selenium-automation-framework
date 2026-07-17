package com.automation.pages;

import com.automation.helper.ElementHelper;
import com.automation.helper.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * BasePage - Abstract Page Object Base Class
 * Provides common functionality shared by all page objects.
 * All page classes should extend this class.
 */
public abstract class BasePage {

    protected WebDriver driver;
    protected WaitHelper waitHelper;
    protected ElementHelper elementHelper;

    /**
     * Constructor initializes driver, helpers, and PageFactory elements.
     */
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
        this.elementHelper = new ElementHelper(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Returns the current page title.
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Returns the current page URL.
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Waits for the page to load by checking the title.
     */
    public void waitForPageLoad(String titleFragment) {
        waitHelper.waitForTitleContains(titleFragment);
    }

    /**
     * Checks if an element is displayed on the page.
     */
    public boolean isElementDisplayed(By locator) {
        return elementHelper.isDisplayed(locator);
    }
}
