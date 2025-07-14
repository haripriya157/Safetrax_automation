package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.json.JSONObject;
import tests.utils.CredentialsUtil;
import org.openqa.selenium.devtools.DevTools;
import org.testng.Assert;
import org.testng.annotations.*;
import org.HomePage;

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

    @Test(priority = 0, description = "Test to verify invalid login functionality")
    public void verifyInvalidLogin() {
        JSONObject creds = CredentialsUtil.getCredentials("invalid");
        homePage.navigateToHomePage();
        assert driver.getCurrentUrl().contains("dev171.safetrax.in");
        homePage.signIn(creds.getString("username"), creds.getString("password"));
        String errorMsg = homePage.getErrorMessage();

        Assert.assertEquals(errorMsg, "Either username or password is wrong");
    }

    @Test(priority = 1, description = "Test to verify navigation to home page and successful login")
    public void verifyValidLogin() {
        JSONObject creds = CredentialsUtil.getCredentials("valid");
        homePage.navigateToHomePage();
        assert driver.getCurrentUrl().contains("dev171.safetrax.in");
        homePage.signIn(creds.getString("username"), creds.getString("password"));
        waitFor5seconds();
        assert driver.getCurrentUrl().contains("https://dev171.safetrax.in/superadmindashboard");
        assert driver.getTitle().equals("Safetrax");

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


