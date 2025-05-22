package com.periplus.pages;

import com.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public class CartPage extends BasePage {
    private By productRowsElement = By.cssSelector("div.row-cart-product");
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
        List<WebElement> productRows = driver.findElements(productRowsElement);

        for (WebElement row : productRows) {
            try {
                // Get the price text (e.g., "Rp 376,000 or 752 Points")
                WebElement priceElement = row.findElement(By.xpath(".//*[contains(text(), 'Rp')]"));
                String fullText = priceElement.getText().trim();
                String[] parts = fullText.split(" ");
                String priceText = parts[1].replace(",", "").trim(); // "265000"
                int unitPrice = Integer.parseInt(priceText);

                // Get the quantity
                WebElement qtyInput = row.findElement(By.cssSelector("input.input-number"));
                int quantity = Integer.parseInt(Objects.requireNonNull(qtyInput.getDomProperty("value")));

                // Calculate the total price
                totalPrice += unitPrice * quantity;
                // System.out.println("unit price = " + unitPrice + " quantity = " + quantity + " total price = " + totalPrice);

            } catch (Exception e) {
                System.out.println("Failed to process a cart item: " + e.getMessage());
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
        // System.out.println("Total value as integer: " + totalValue);
        return Integer.parseInt(numericText);
    }
}
