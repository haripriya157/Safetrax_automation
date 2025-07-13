package org;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private String url = "https://dev171.safetrax.in";

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHomePage() {
        driver.get(url);
        // Wait for up to 10 seconds for the page URL to match
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
            (ExpectedCondition<Boolean>) d -> d.getCurrentUrl().contains("dev171.safetrax.in")
        );

    }
    public void enterUsername(String username) {
        driver.findElement(By.id("user")).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(By.id("password")).sendKeys(password);
    }

    public void clickSubmit() {
        driver.findElement(By.id("formsubmit")).click();
    }

    public void signIn(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickSubmit();
    }

    // In HomePage.java
    public void logout() {
        driver.findElement(By.xpath("//div[@class='cookie-user-id user-id-new-ui']")).click();
        driver.findElement(By.id("gotologout")).click();
    }

    // Add this method to get error message after failed login
    public String getErrorMessage() {
        WebElement errorMsg = driver.findElement(By.xpath("//*[text()='Either username or password is wrong  ']")); // Change selector if needed
        return errorMsg.getText();
    }
}
