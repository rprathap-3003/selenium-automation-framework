package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * LoginPage - Page Object for Login Page
 * Demonstrates the Page Object Model (POM) design pattern.
 * Update locators to match your application's login page.
 */
public class LoginPage extends BasePage {

    // ===== Locators =====
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("loginBtn");
    private final By errorMessage = By.id("errorMessage");
    private final By welcomeMessage = By.id("welcomeMessage");

    /**
     * Constructor initializes the page with the WebDriver.
     */
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Enters username into the username field.
     */
    public LoginPage enterUsername(String username) {
        elementHelper.type(usernameField, username);
        return this;
    }

    /**
     * Enters password into the password field.
     */
    public LoginPage enterPassword(String password) {
        elementHelper.type(passwordField, password);
        return this;
    }

    /**
     * Clicks the login button.
     */
    public LoginPage clickLogin() {
        elementHelper.click(loginButton);
        return this;
    }

    /**
     * Performs a complete login action.
     *
     * @param username the username to enter
     * @param password the password to enter
     * @return this LoginPage instance for method chaining
     */
    public LoginPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return this;
    }

    /**
     * Returns the error message text if displayed.
     */
    public String getErrorMessage() {
        return elementHelper.getText(errorMessage);
    }

    /**
     * Returns the welcome message text after successful login.
     */
    public String getWelcomeMessage() {
        return elementHelper.getText(welcomeMessage);
    }

    /**
     * Checks if the error message is displayed.
     */
    public boolean isErrorMessageDisplayed() {
        return elementHelper.isDisplayed(errorMessage);
    }

    /**
     * Checks if the login button is displayed (page loaded).
     */
    public boolean isLoginPageDisplayed() {
        return elementHelper.isDisplayed(loginButton);
    }
}
