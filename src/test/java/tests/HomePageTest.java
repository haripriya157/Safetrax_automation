package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import org.HomePage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HomePageTest {
    private WebDriver driver;
    private HomePage homePage;
    private String username;
    private String password;
    private String invalidUsername;
    private String invalidPassword;

    @BeforeClass
    public void setUp() {
        // Initialize WebDriver
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize(); // Maximize window to see UI
        homePage = new HomePage(driver);

        // Load credentials from env-config.ini
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/test/resources/dev171/env-config.ini")) {
            properties.load(fis);
            username = properties.getProperty("username").trim();
            password = properties.getProperty("password").trim();
            invalidUsername = properties.getProperty("invalidUsername").trim();
            invalidPassword = properties.getProperty("invalidPassword").trim();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load credentials from env-config.ini", e);
        }
    }

    @Test(priority = 0)
    public void verifyHomePageContent() {
        homePage.navigateToHomePage();
        Assert.assertTrue(driver.getPageSource().contains("Unified Platform for Employee Transport Automation"));
    }

    @Test(priority = 1)
    public void verifyInvalidLogin() {
        homePage.navigateToHomePage();
        homePage.signIn(invalidUsername, invalidPassword);
        String errorMsg = homePage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Either username or password is wrong"));
    }

    @Test(priority = 2)
    public void verifyForgotPasswordLink() {
        homePage.navigateToHomePage();
        homePage.clickForgotPassword();
        Assert.assertTrue(driver.getCurrentUrl().contains("auth/resetpassword"));
    }

    @Test(priority = 3)
    public void verifyValidLogin() {
        homePage.navigateToHomePage();
        homePage.signIn(username, password);
        Assert.assertTrue(driver.getCurrentUrl().contains("superadmindashboard"));
        Assert.assertEquals(driver.getTitle(), "Safetrax");
        homePage.logout();
        waitFor5seconds();
    }

    public void waitFor5seconds() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}