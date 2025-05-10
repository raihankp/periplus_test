package com.periplus.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductDetailPage extends BasePage {
    private By addToCartButtonElement = By.className("btn-add-to-cart");
    private By bookTitlesInCartElement = By.xpath("//*[@class='shopping-list']//li//div[2]//a");
    private By cartIconElement = By.id("show-your-cart");
    private By modalTextElement = By.className("modal-text");
    private By bookTitlesInProductDetailElement = By.xpath("/html/body/div[3]/div[1]/div/div/div[2]/h2");

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public void clickAddToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonElement));
        addToCartButton.click();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getAddToCartModalText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the modal to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(modalTextElement));

        return find(modalTextElement).getText();
    }

    public void waitForAddToCartModalToDisappear() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(modalTextElement));
    }

    public void hoverCartIcon() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));
        WebElement cartIcon = find(cartIconElement);
        Actions actions = new Actions(driver);
        actions.moveToElement(cartIcon).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(bookTitlesInCartElement));
    }

    public String getBookTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement titleElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(bookTitlesInProductDetailElement)
        );
        return titleElement.getText().trim();
    }

    public boolean isBookTitleInCart(String expectedBookTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
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
