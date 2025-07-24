package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.json.JSONObject;
import tests.utils.CredentialsUtil;
import org.testng.Assert;
import org.testng.annotations.*;
import org.HomePage;


//------------------------------------------------------
//author: HariPriya M
//date: 14-07-2025
//
//-------------------------------------------------------
/**
 * Test class for verifying the functionality of the HomePage.
 */

public class HomePageTest {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeClass
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().window().maximize(); // Maximize window to see UI
        homePage = new HomePage(driver);
    }

    @Test(priority = 0)
    public void verifyHomePageContent() {
        homePage.navigateToHomePage();
        assert driver.getCurrentUrl().contains("http://10.10.100.171:3004/");
        String actualText = driver.findElement(By.xpath("//div[contains(text(),'Unified Platform')]")).getText().trim();
        Assert.assertTrue(actualText.toLowerCase().contains("unified platform for employee transport automation"));
    }

    @Test(priority = 1)
    public void verifyInvalidLogin() {
        JSONObject creds = CredentialsUtil.getCredentials("invalid");
        homePage.navigateToHomePage();
        assert driver.getCurrentUrl().contains("http://10.10.100.171:3004/auth?redirectTo=/");
        homePage.signIn(creds.getString("username"), creds.getString("password"));
        String errorMsg = homePage.getErrorMessage();
        Assert.assertTrue(errorMsg.contains("Either username or password is wrong"));
    }

    @Test(priority = 2)
    public void verifyForgotPasswordLink() {
        homePage.navigateToHomePage();
        assert driver.getCurrentUrl().contains("http://10.10.100.171:3004/auth?redirectTo=/");
        homePage.clickForgotPassword();
        assert driver.getCurrentUrl().contains("auth/resetpassword");
    }

    @Test(priority = 3)
    public void verifyValidLogin() {
        JSONObject creds = CredentialsUtil.getCredentials("valid");
        homePage.navigateToHomePage();
        assert driver.getCurrentUrl().contains("http://10.10.100.171:3004/auth?redirectTo=/");
        homePage.signIn(creds.getString("username"), creds.getString("password"));
        // Wait for dashboard page to load
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        Assert.assertTrue(driver.getCurrentUrl().contains("http://10.10.100.171:3004/superadmindashboard"));
        Assert.assertEquals(driver.getTitle(), "Safetrax");
        homePage.logout();
        waitFor5seconds();
    }

    public void waitFor5seconds(){
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
