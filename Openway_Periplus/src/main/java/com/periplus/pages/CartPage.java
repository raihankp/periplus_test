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
    private By cartListBooksElement = By.xpath("//*[@class='row row-cart-product']//p//a");
    private By plusButton = By.xpath("(//*[@class='row row-cart-product'])[1]//div[@class='button plus']");
    private By quantityField = By.xpath("(//*[@class='row row-cart-product'])[1]//input[@class='input-number text-center']");
    private By priceElements  = By.xpath("//div[contains(@class, 'shopping-summery')]//div[contains(@class, 'row-cart-product')]//*[contains(text(), 'Rp')]");
    private By totalPriceElement = By.xpath("//li[starts-with(normalize-space(), 'Total')]/span");
    private By removeProductsElement = By.xpath("//a[contains(@class, 'btn-cart-remove')]");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void appendQuantity(String expectedBookAppended) {
        click(plusButton);
    }

    public int getQuantity() {
        return Integer.parseInt(find(quantityField).getDomProperty("value"));
    }

    public void clearAllCartItems() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        while (true) {
            List<WebElement> removeButtons = findAll(removeProductsElement);
            if (removeButtons.isEmpty()) {
                // Cart is empty
                break;
            }

            WebElement removeButton = removeButtons.getFirst();
            removeButton.click();

            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("preloader")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

            wait.until(ExpectedConditions.stalenessOf(removeButton));
        }

    }

    public void removeBook(String expectedBookRemoved) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        List<WebElement> cartBookTitles  = findAll(cartListBooksElement);
        for (WebElement titleElement : cartBookTitles) {
            if (titleElement.getText().equalsIgnoreCase(expectedBookRemoved)) {
                // Go up to the book row element (3 levels up)
                WebElement bookRow = titleElement.findElement(By.xpath("./ancestor::*[contains(@class, 'row-cart-product')]"));

                WebElement removeButton = bookRow.findElement(By.cssSelector(".btn-cart-remove"));
                removeButton.click();

                wait.until(ExpectedConditions.presenceOfElementLocated(By.className("preloader")));
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

                wait.until(ExpectedConditions.stalenessOf(bookRow));
                break;
            }
        }
    }

    public boolean isBookRemoved(String expectedBookRemoved) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<WebElement> cartBookTitles = findAll(cartListBooksElement);
        return cartBookTitles.stream()
                .map(WebElement::getText)
                .noneMatch(title -> title.equalsIgnoreCase(expectedBookRemoved));
    }

    public int getAllPrices() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        List<WebElement> allProductPrice = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(priceElements));

        int totalPrice = 0;

        for (WebElement productPrice : allProductPrice) {
            String fullText = productPrice.getText().trim();  // "Rp 376,000 or 752 Points"
            // System.out.print(fullText);
            if (fullText.contains("Rp")) {
                String[] parts = fullText.split(" ");
                if (parts.length >= 2) {
                    String priceText = parts[1].replace(",", "").trim(); // "265000"
                    try {
                        int price = Integer.parseInt(priceText);
                        totalPrice += price;
                        // System.out.println("Parsed price: " + price);
                    } catch (NumberFormatException e) {
                        // System.out.println("Failed to parse price: " + priceText);
                    }
                }
            }
        }

        // System.out.println("Total Price of the books: " + totalPrice);
        return totalPrice;
    }

    public int getTotalPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloader")));

        WebElement totalPrice = wait.until(ExpectedConditions.presenceOfElementLocated(totalPriceElement));

        String totalText = totalPrice.getText();
        String numericText = totalText.replaceAll("[^\\d]", ""); // Hanya ambil digit
        int totalValue = Integer.parseInt(numericText);
        // System.out.println("Total value as integer: " + totalValue);
        return totalValue;
    }
}
