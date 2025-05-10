package com.periplus.tests;

import com.periplus.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {
    @Test(description = "TC_CART_001 - Remove Book frfom Cart Page")
    public void testRemoveBookFromCartPage() {
        loginPage = homePage.clickLoginLink();
        accountPage = loginPage.login("raihankp96@gmail.com", "raihankp123");
        homePage = accountPage.clickHomePage();
        productDetailPage = homePage.clickProductTitle(1);

        productDetailPage.clickAddToCart();
        productDetailPage.getAddToCartModalText();
        productDetailPage.waitForAddToCartModalToDisappear();
        String expectedBookRemoved = productDetailPage.getBookTitle();

        cartPage = productDetailPage.clickCartButton();

        cartPage.removeBook(expectedBookRemoved);
        Assert.assertTrue(cartPage.isBookRemoved(expectedBookRemoved));
    }
}
