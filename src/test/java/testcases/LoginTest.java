package testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utilities.BaseTest;

public class LoginTest extends BaseTest {
    LoginPage loginPage;
    @BeforeMethod
    public void setupPage(){
        loginPage = new LoginPage(driver);
    }
    //Test Case 1 : Valid Login with standard_user(positive tc)
    @Test(groups = {"smoke"})
    public void validLogin(){
        loginPage.login("standard_user" ,"secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
        System.out.println("Driver in LoginPage = " + driver);
    }

    //Test Case 2 : Login with locked_out_user(negative tc)
    @Test
    public void lockedOutUserLogin(){
        loginPage.login("locked_out_user" , "secret_sauce");
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Sorry, this user has been locked out"));
    }

    //Test Case 3 : Login with invalid username(negative tc)
    @Test
    public void invalidUsernameLogin(){
        loginPage.login("invalid_user" , "secret_sauce");
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Username and password do not match"));
    }

    //Test Case 4 : Login with invalid password(negative tc)
    @Test
    public void invalidPasswordLogin(){
        loginPage.login("standard_user", "wrong_password");
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Username and password do not match"));
    }

    //Test Case 5: Login with empty username(negative tc)
    @Test
    public void emptyUsername(){
        loginPage.login("" , "secret_sauce");
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Username is required"));
    }

    //Test Case 6: Login with empty password(negative tc)
    @Test
    public void emptyPassword(){
        loginPage.login("standard_user", "");
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Password is required"));
    }

    //Test Case 7: Login with empty username and password(negative tc)
    @Test
    public void emptyUsernameAndPassword(){
        loginPage.login("", "");
        Assert.assertTrue(loginPage.getErrorMessage()
                .contains("Username is required"));
    }

    //Test Case 8: Login with problem_user
    @Test
    public void loginWithProblemUser(){
        loginPage.login("problem_user" , "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl()
                .contains("inventory"));
    }

    //Test Case 9: Verify Login Page Title(positive tc )
    @Test
    public void verifyLoginPageTitle(){
        Assert.assertEquals(driver.getTitle(), "Swag Labs");
    }

    //Test case 10 : Login with performance_glitch_user(positive tc)
    @Test
    public void loginWithPerformanceGlitchUser() {
        loginPage.login("performance_glitch_user", "secret_sauce");
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    // Test case 11 : Login with spaces only in username and password(negative tc)
    @Test
    public void loginWithSpacesOnlyShouldShowUsernameRequiredError() {
        loginPage.login("   ", "   ");

        Assert.assertTrue(
                loginPage.getErrorMessage()
                        .contains("Username is required")
        );
    }
}
