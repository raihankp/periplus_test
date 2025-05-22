package com.periplus.tests;

import com.periplus.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {
    @Test(description = "TC_ADDTOCART_001 - Add product to Cart by Hover")
    public void addToCartByHover() {
        homePage.HoverProductTitle(1);
        homePage.clickAddToCartByIndex(1);
        String expectedBookTitle = homePage.getBookTitleAdded(1);

        homePage.hoverCartIcon();
        Assert.assertTrue(homePage.isBookTitleInCart(expectedBookTitle));
    }

    @Test(description = "TC_ADDTOCART_002 - Add product to Cart by Product Detail Page")
    public void addToCartByProductDetailPage() {
        String expectedBookTitle = homePage.getBookTitleAdded(2);
        productDetailPage = homePage.clickProductTitle(2);

        productDetailPage.clickAddToCart();
        String modalText = productDetailPage.getAndWaitForAddToCartModalText();

        productDetailPage.hoverCartIcon();
        Assert.assertEquals(modalText, "Success add to cart");
        Assert.assertTrue(productDetailPage.isBookTitleInCart(expectedBookTitle));
    }

    @Test(description = "TC_ADDTOCART_003 - Consistency If Product Still in Cart After Logout")
    public void testLogoutConsistency() {
        homePage.clickHomePage();
        homePage.HoverProductTitle(3);
        homePage.clickAddToCartByIndex(3);
        String expectedBookTitle = homePage.getBookTitleAdded(3);
        homePage.clickLogoutLink();

        loginPage = homePage.clickLoginLink();
        accountPage = loginPage.login("raihankp96@gmail.com", "raihankp123");

        homePage.hoverCartIcon();
        Assert.assertTrue(homePage.isBookTitleInCart(expectedBookTitle));
    }

}
