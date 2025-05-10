package com.periplus.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage extends BasePage {
    private By periplusLogoNavbarElement = By.xpath("//div[contains(@class, 'logo-new')]/a");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAccountPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        return titleMatch("My Account");
    }

    public HomePage clickHomePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        WebElement periplusLogoNavbar = wait.until(ExpectedConditions.elementToBeClickable(periplusLogoNavbarElement));
        periplusLogoNavbar.click();
        return new HomePage(driver);
    }
}
