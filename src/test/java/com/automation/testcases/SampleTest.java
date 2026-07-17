package com.automation.testcases;

import com.automation.base.BaseTest;
import com.automation.helper.ElementHelper;
import com.automation.helper.WaitHelper;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SampleTest - Smoke Test Suite
 * Demonstrates basic framework usage with a Google search test.
 */
public class SampleTest extends BaseTest {

    @Test(priority = 1, description = "Verify Google homepage loads successfully")
    public void testGoogleHomePageTitle() {
        extentTest.log(Status.INFO, "Starting Google homepage title verification");

        // Navigate to Google
        navigateToBaseUrl();

        // Verify page title
        String pageTitle = driver.getTitle();
        extentTest.log(Status.INFO, "Page title: " + pageTitle);

        Assert.assertTrue(pageTitle.toLowerCase().contains("google"),
                "Expected 'Google' in title but found: " + pageTitle);

        extentTest.log(Status.PASS, "Google homepage loaded successfully with correct title");
    }

    @Test(priority = 2, description = "Verify Google search returns results")
    public void testGoogleSearch() {
        extentTest.log(Status.INFO, "Starting Google search test");

        // Navigate to Google
        navigateToBaseUrl();

        // Initialize helpers
        ElementHelper elementHelper = new ElementHelper(driver);
        WaitHelper waitHelper = new WaitHelper(driver);

        // Search for a term
        By searchBox = By.name("q");
        elementHelper.type(searchBox, "Selenium WebDriver" + Keys.ENTER);
        extentTest.log(Status.INFO, "Searched for: Selenium WebDriver");

        // Wait for results page
        waitHelper.waitForTitleContains("Selenium WebDriver");

        // Verify search results page title
        String resultsTitle = driver.getTitle();
        extentTest.log(Status.INFO, "Results page title: " + resultsTitle);

        Assert.assertTrue(resultsTitle.contains("Selenium WebDriver"),
                "Search results page title should contain 'Selenium WebDriver'");

        extentTest.log(Status.PASS, "Google search completed successfully");
    }

    @Test(priority = 3, description = "Verify current URL contains google.com")
    public void testGoogleUrl() {
        extentTest.log(Status.INFO, "Starting URL verification test");

        // Navigate to Google
        navigateToBaseUrl();

        // Verify URL
        String currentUrl = driver.getCurrentUrl();
        extentTest.log(Status.INFO, "Current URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("google.com"),
                "URL should contain 'google.com' but was: " + currentUrl);

        extentTest.log(Status.PASS, "URL verified successfully");
    }
}
