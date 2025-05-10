package com.periplus.tests;

import com.periplus.base.BaseTest;
import com.periplus.pages.AccountPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test(description = "TC_LOGIN_001 - Login Successful")
    public void testLoginSuccessful() {
        loginPage = homePage.clickLoginLink();
        accountPage = loginPage.login("raihankp96@gmail.com", "raihankp123");
        Assert.assertTrue(accountPage.isAccountPage());
    }

    @Test(description = "TC_LOGIN_002 - Login Failed")
    public void testLoginFailed() {
        loginPage = homePage.clickLoginLink();
        loginPage.login("raihankp96@gmail.com", "12345678");
        String actualErrorMessage = loginPage.getErrorMessage();
        String expectedErrorMessage = "Warning: No match for Email Address and/or Password.";
        Assert.assertTrue(actualErrorMessage.contains(expectedErrorMessage));
    }
}
