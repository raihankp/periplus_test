package com.periplus.tests;

import com.periplus.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {
    @Test(description = "TC_ADDTOCART_001 - Add product to Cart by Hover")
    public void addToCartByHover() {
        loginPage = homePage.clickLoginLink();
        accountPage = loginPage.login("raihankp96@gmail.com", "raihankp123");
        homePage = accountPage.clickHomePage();

        homePage.HoverProductTitle(1);
        homePage.clickAddToCart(1);
        String expectedBookTitle = homePage.getBookTitleAdded(1);

        homePage.hoverCartIcon();
        Assert.assertTrue(homePage.isBookTitleInCart(expectedBookTitle));
    }

    @Test(description = "TC_ADDTOCART_002 - Add product to Cart by Product Detail Page")
    public void addToCartByProductDetailPage() {
        loginPage = homePage.clickLoginLink();
        accountPage = loginPage.login("raihankp96@gmail.com", "raihankp123");
        homePage = accountPage.clickHomePage();
        String expectedBookTitle = homePage.getBookTitleAdded(1);
        productDetailPage = homePage.clickProductTitle(1);

        productDetailPage.clickAddToCart();
        String modalText = productDetailPage.getAddToCartModalText();
        productDetailPage.waitForAddToCartModalToDisappear();

        productDetailPage.hoverCartIcon();
        Assert.assertEquals(modalText, "Success add to cart");
        Assert.assertTrue(productDetailPage.isBookTitleInCart(expectedBookTitle));
    }

}
