package org;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private String url = "http://10.10.100.171:3004/";

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHomePage() {
        driver.get(url);
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            d -> d.getCurrentUrl().contains("http://10.10.100.171:3004/")
        );
        // Wait for username field to be visible
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

    public void logout() {
        // Wait for user icon to be clickable
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='cookie-user-id user-id-new-ui']")))
            .click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(By.id("gotologout")))
            .click();
    }

    public String getErrorMessage() {
        // Wait for error message to be visible
        WebElement errorMsg = new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Either username or password is wrong')]")));
        return errorMsg.getText().trim();
    }

    public void clickForgotPassword() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='forgot']/a[@href='/auth/resetpassword']")))
            .click();
    }
    public void resetPassword(String username) {
        WebElement usernameInput = new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='user']")));
        usernameInput.clear();
        usernameInput.sendKeys(username);
        // Update selector for reset password button (use actual id or text)
        WebElement resetBtn = new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'RESET PASSWORD')]")));
        resetBtn.click();
    }

    public String getResetPasswordMessage() {
        WebElement msg = new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='infoMessage']")));
        return msg.getText();
    }
}
