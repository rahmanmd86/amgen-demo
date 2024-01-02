package com.ambrygen.that.pages;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {

    @FindBy
    By cartPage = By.cssSelector("div.wag-cart-container");

    @FindBy
    By qtyPlus = By.cssSelector("div.quantity__buttons button.qtyplus");

    @FindBy
    By productPriceInCart = By.cssSelector("div.wag-cart-prd-price span.product__price");

    public CartPage(WebDriver driver) {
        super(driver);
        // No assertions, throws an exception if the element is not loaded
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(d -> d.findElement(cartPage));
    }

    public void incrementQuantity(int quantity) throws InterruptedException {
        for (int i = 1; i < quantity; i++) {
            driver.findElement(qtyPlus).click();
            Thread.sleep(3000);
        }
    }

    public BigDecimal getPrice() {
        String productPrice = driver.findElement(productPriceInCart).getText().replace("$", "");
        BigDecimal prdPrice = new BigDecimal(productPrice).setScale(2, RoundingMode.UNNECESSARY);
        return prdPrice;
    }

    
}
