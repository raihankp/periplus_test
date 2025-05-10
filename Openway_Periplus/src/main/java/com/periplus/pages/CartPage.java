package com.periplus.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage {
    private By cartBooksRowElement = By.xpath("//*[@class='row row-cart-product']");
    private By cartListBooksElement = By.xpath("//*[@class='row row-cart-product']//p//a");
    private By quantityElement = By.xpath("(//*[@class='row row-cart-product'])[1]//input[@class='input-number text-center']");
    private By plusButtonElement = By.xpath("(//*[@class='row row-cart-product'])[1]//div[@class='button plus']");
    private By bookRemoveButtonElement = By.xpath("(//*[@class='row row-cart-product'])[1]//*[@class='btn btn-cart-remove']");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void removeBook(String expectedBookRemoved) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        List<WebElement> cartBookTitles  = findAll(cartListBooksElement);
        System.out.println(cartBookTitles.size());
        for (WebElement titleElement : cartBookTitles) {
            System.out.println(titleElement.getText());
            System.out.println(expectedBookRemoved);
            System.out.println("\n");
            if (titleElement.getText().equalsIgnoreCase(expectedBookRemoved)) {
                System.out.println("MASUKK");
                // Go up to the book row element (3 levels up)
                WebElement bookRow = titleElement.findElement(By.xpath("./ancestor::*[contains(@class, 'row-cart-product')]"));

                WebElement removeButton = bookRow.findElement(By.cssSelector(".btn-cart-remove"));
                removeButton.click();
                wait.until(ExpectedConditions.stalenessOf(bookRow));
                break;
            }
        }
    }

    public boolean isBookRemoved(String expectedBookRemoved) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        List<WebElement> cartBookTitles = findAll(cartListBooksElement);
        return cartBookTitles.stream()
                .map(WebElement::getText)
                .noneMatch(title -> title.equalsIgnoreCase(expectedBookRemoved));
    }
}
