package com.periplus.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage extends BasePage {
    private By loginElement = By.cssSelector("#nav-signin-text a");
    private By navAccountTextElement = By.className("nav-button-title");
    private By logoutElement = By.cssSelector(".shopping-item a[href*='Logout']");
    private By bookTitleElement(int index) {
            return By.xpath("(//*[@class='owl-item active'])[" + index + "]//*[@class='product-content product-contents']/h3");
    }
    private By addToCartElement(int index) {
        return By.xpath("(//*[@class='owl-item active'])[" + index + "]//a[contains(@class, 'addtocart')]");
    }
    private By cartIconElement = By.id("show-your-cart");
    private By bookTitlesInCartElement = By.xpath("//*[contains(@class, 'shopping-list')]//a");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickLoginLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        click(loginElement);
        return new LoginPage(driver);
    }

    public void clickLogoutLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        WebElement navAccountText = find(navAccountTextElement);
        Actions actions = new Actions(driver);

        actions.moveToElement(navAccountText).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(logoutElement));

        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(logoutElement));

        logoutButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("preloader")));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        clickHomePage();
    }

    public ProductDetailPage clickProductTitle(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        WebElement book = wait.until(ExpectedConditions.elementToBeClickable(bookTitleElement(index)));
        book.click();
        return new ProductDetailPage(driver);
    }
    public void HoverProductTitle(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        WebElement book = wait.until(ExpectedConditions.elementToBeClickable(bookTitleElement(index)));

        Actions action = new Actions(driver);
        action.moveToElement(book).perform();
    }

    public void clickAddToCartByIndex(int index) {
        click(addToCartElement(index));
    }

    public void hoverCartIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(cartIconElement));

        Actions actions = new Actions(driver);
        actions.moveToElement(cartIcon).perform();

        wait.until(ExpectedConditions.presenceOfElementLocated(bookTitlesInCartElement));
        wait.until(ExpectedConditions.visibilityOfElementLocated(bookTitlesInCartElement));
    }

    public String getBookTitleAdded(int index) {
        return find(bookTitleElement(index)).getText();
    }

    public boolean isBookTitleInCart(String expectedBookTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(bookTitlesInCartElement));

        List<WebElement> bookTitlesInCart = findAll(bookTitlesInCartElement);

        return bookTitlesInCart.stream()
                .map(WebElement::getText)
                .anyMatch(title -> title.equalsIgnoreCase(expectedBookTitle));
    }

}
