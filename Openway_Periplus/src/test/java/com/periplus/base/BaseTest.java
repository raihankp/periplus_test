package com.periplus.base;

import com.base.BasePage;
import com.periplus.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected BasePage basePage;
    protected LoginPage loginPage;
    protected HomePage homePage;
    protected AccountPage accountPage;
    protected ProductDetailPage productDetailPage;
    protected CartPage cartPage;
    private String url = "https://www.periplus.com";

    @BeforeClass
    public void setUpClass() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        homePage = new HomePage(driver);

        // Login once
        loginPage = homePage.clickLoginLink();
        accountPage = loginPage.login("raihankp96@gmail.com", "raihankp123");
        homePage = accountPage.clickHomePage();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
