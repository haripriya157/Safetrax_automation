package org;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class HomePage {
    private WebDriver driver;
    private String url;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.url = loadBaseUrl();
    }

    private String loadBaseUrl() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/dev171/env-config.ini")) {
            properties.load(fis);
            return properties.getProperty("baseUrl").trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load base URL from env-config.ini", e);
        }
    }

    public void navigateToHomePage() {
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                d -> d.getCurrentUrl().contains(url)
        );
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(By.id("user"))
        );
    }

    public void enterUsername(String username) {
        WebElement userField = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("user")));
        userField.clear();
        userField.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement passField = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        passField.clear();
        passField.sendKeys(password);
    }

    public void clickSubmit() {
        WebElement submitBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("formsubmit")));
        submitBtn.click();
    }

    public void signIn(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSubmit();
    }

    public String getErrorMessage() {
        WebElement errorElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Either username or password is wrong')]")));
        return errorElement.getText();
    }

    public void clickForgotPassword() {
        WebElement forgotPasswordLink = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='forgot']/a[@href='/auth/resetpassword']")));
        forgotPasswordLink.click();
    }

    public void logout() {
        WebElement loggedUser = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='cookie-user-id user-id-new-ui']"))); // Update ID if necessary
        loggedUser.click();

        WebElement logoutButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("gotologout"))); // Update ID if necessary
        logoutButton.click();
    }
}