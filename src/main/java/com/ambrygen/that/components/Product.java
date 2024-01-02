package com.ambrygen.that.components;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

// Page Component Object
public class Product extends BaseComponent {

    @FindBy
    By productTitle = By.cssSelector("div.product__details div.product__title");

    @FindBy
    By productDetails = By.cssSelector("div.product__details");

    // The root element contains the entire component
    public Product(WebElement root) {
        super(root); // inventory_item
    }

    //li.card__product

    public String getProductDetails() {
        // Locating an element begins at the root of the component
        return root.findElement(productDetails).getText();
    }

    public String getProductTitle() {
        // Locating an element begins at the root of the component
        return root.findElement(productTitle).getText();
    }

    public BigDecimal getPrice() {
        return new BigDecimal(
                root.findElement(By.cssSelector("div.product__price-contain span.product__price"))
                    .getText()
                    .replace("$", "")
            ).setScale(2, RoundingMode.UNNECESSARY); // Sanitation and formatting
    }

    public WebElement addToCart() {
        return root.findElement(By.cssSelector("div.product__buttons button.btn__blue"));
    }
}
