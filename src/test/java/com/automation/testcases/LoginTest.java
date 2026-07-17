package com.automation.testcases;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.aventstack.extentreports.Status;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;

/**
 * LoginTest - Login Page Test Suite
 * Demonstrates Page Object Model (POM) usage with data-driven testing.
 * Uses TestNG @DataProvider to read test data from testdata.json.
 *
 * NOTE: Update the login URL and LoginPage locators to match your application.
 */
public class LoginTest extends BaseTest {

    /**
     * DataProvider that reads login credentials from testdata.json.
     */
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        try {
            JSONParser parser = new JSONParser();
            JSONObject testData = (JSONObject) parser.parse(
                    new FileReader("src/test/resources/testdata/testdata.json"));

            JSONArray credentials = (JSONArray) testData.get("loginCredentials");
            Object[][] data = new Object[credentials.size()][3];

            for (int i = 0; i < credentials.size(); i++) {
                JSONObject cred = (JSONObject) credentials.get(i);
                data[i][0] = cred.get("username").toString();
                data[i][1] = cred.get("password").toString();
                data[i][2] = cred.get("expectedResult").toString();
            }

            return data;
        } catch (Exception e) {
            System.err.println("Failed to read test data: " + e.getMessage());
            // Return default test data if file read fails
            return new Object[][]{
                    {"testuser", "testpass", "success"}
            };
        }
    }

    @Test(priority = 1, description = "Verify login page is accessible")
    public void testLoginPageLoads() {
        extentTest.log(Status.INFO, "Navigating to login page");

        // Navigate to login URL (update this to your application's login page)
        String loginUrl = config.getProperty("base.url", "https://www.google.com");
        driver.get(loginUrl);

        extentTest.log(Status.INFO, "Navigated to: " + loginUrl);

        // Verify page loaded by checking the title is not empty
        String pageTitle = driver.getTitle();
        Assert.assertNotNull(pageTitle, "Page title should not be null");
        Assert.assertFalse(pageTitle.isEmpty(), "Page title should not be empty");

        extentTest.log(Status.PASS, "Login page loaded successfully. Title: " + pageTitle);
    }

    @Test(priority = 2, dataProvider = "loginData",
            description = "Data-driven login test with multiple credentials")
    public void testLoginWithCredentials(String username, String password, String expectedResult) {
        extentTest.log(Status.INFO, "Testing login with user: " + username);

        // Navigate to login page
        String loginUrl = config.getProperty("base.url", "https://www.google.com");
        driver.get(loginUrl);

        // Use Page Object Model
        LoginPage loginPage = new LoginPage(driver);

        // Perform login
        loginPage.login(username, password);
        extentTest.log(Status.INFO, "Login attempted with username: " + username);

        // Verify result based on expected outcome
        if ("success".equals(expectedResult)) {
            extentTest.log(Status.INFO, "Expecting successful login for user: " + username);
            // In a real application, verify welcome message or dashboard
            // Assert.assertTrue(loginPage.getWelcomeMessage().contains("Welcome"));
        } else {
            extentTest.log(Status.INFO, "Expecting failed login for user: " + username);
            // In a real application, verify error message
            // Assert.assertTrue(loginPage.isErrorMessageDisplayed());
        }

        extentTest.log(Status.PASS, "Login test completed for user: " + username
                + " with expected result: " + expectedResult);
    }

    @Test(priority = 3, description = "Verify page title is not empty after navigation")
    public void testPageTitleExists() {
        extentTest.log(Status.INFO, "Starting page title verification");

        navigateToBaseUrl();

        // Verify page title exists
        String title = driver.getTitle();
        extentTest.log(Status.INFO, "Page title: " + title);

        Assert.assertNotNull(title, "Page title should not be null");
        Assert.assertFalse(title.isEmpty(), "Page title should not be empty");

        extentTest.log(Status.PASS, "Page title verified: " + title);
    }
}
