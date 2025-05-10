package com.periplus.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
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
    private By bookTitleElement(int index) {
            return By.xpath("(//*[@class='owl-item active'])[" + index + "]//*[@class='product-content product-contents']/h3");
    }
    private By addToCartElement(int index) {
        return By.xpath("(//*[@class='owl-item active'])[" + index + "]//a[contains(@class, 'addtocart')]");
    }
    private By cartIconElement = By.id("show-your-cart");
    private By bookTitlesInCartElement = By.xpath("//*[@class='shopping-list']//li//div[2]//a");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public LoginPage clickLoginLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        click(loginElement);
        return new LoginPage(driver);
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

    public void clickAddToCart(int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartElement(index)));
        addToCartButton.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hoverCartIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        WebElement cartIcon = find(cartIconElement);
        Actions actions = new Actions(driver);
        actions.moveToElement(cartIcon).pause(Duration.ofSeconds(1)).perform();
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

    public CartPage clickCartButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(cartIconElement));
        cartButton.click();
        return new CartPage(driver);
    }
}
