package com.periplus.tests;

import com.periplus.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class ShoppingCartTest extends BaseTest {
    private String bookTitle;

    @BeforeMethod
    public void setUpCart() {
        homePage.clickHomePage();
        homePage.HoverProductTitle(1);
        homePage.clickAddToCartByIndex(1);
        bookTitle = homePage.getBookTitleAdded(1);
    }

    @AfterMethod
    public void clearCart() {
        cartPage.clearAllCartItems(); // To make fresh state again
    }


    @Test(description = "TC_CART_001 - Update Book Quantity from Cart Page", priority = 1)
    public void testUpdateBookQuantityFromCartPage() {
        cartPage = homePage.clickCartButton();
        int oldQuantity = cartPage.getQuantity();
        cartPage.appendQuantity(bookTitle);
        int newQuantity =cartPage.getQuantity();

        Assert.assertEquals(newQuantity, oldQuantity + 1);
    }

    @Test(description = "TC_CART_002 - Remove Book from Cart Page", priority = 2)
    public void testRemoveBookFromCartPage() {
        cartPage = homePage.clickCartButton();
        cartPage.removeBook(bookTitle);
        Assert.assertTrue(cartPage.isBookRemoved(bookTitle));
    }

    @Test(description = "TC_CART_003 - Confirm Total Price", priority = 3)
    public void testConfirmTotalPrice() {
        // Add another book
        homePage.clickAddToCartByIndex(2);
        homePage.clickAddToCartByIndex(3);
        cartPage = homePage.clickCartButton();
        int allPrice = cartPage.getAllPrices();
        int expectedTotalPrice = cartPage.getTotalPrice();

        Assert.assertEquals(allPrice, expectedTotalPrice);
    }
}
