package com.base;

import com.periplus.pages.CartPage;
import com.periplus.pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class BasePage {
    protected WebDriver driver;
    private By periplusLogoNavbarElement = By.xpath("//div[contains(@class, 'logo-new')]/a");
    private By cartIconElement = By.id("show-your-cart");

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebElement find(By locator) {
        return this.driver.findElement(locator);
    }

    protected List<WebElement> findAll(By locator) {
        return this.driver.findElements(locator);
    }

    protected void set(By locator, String value) {
        this.driver.findElement(locator).sendKeys(value);
    }

    protected void click(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected boolean titleMatch(String title) {
        return Objects.equals(this.driver.getTitle(), title);
    }

    public HomePage clickHomePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        WebElement periplusLogoNavbar = wait.until(ExpectedConditions.elementToBeClickable(periplusLogoNavbarElement));
        periplusLogoNavbar.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        return new HomePage(driver);
    }

    public CartPage clickCartButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(cartIconElement));
        cartButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("preloader")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        return new CartPage(driver);
    }
 }
