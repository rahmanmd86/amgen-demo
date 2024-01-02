package com.ambrygen.that.pages;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ambrygen.that.components.Fulfillment;
import com.ambrygen.that.components.Product;

public class SearchResultPage extends BasePage {

    @FindBy
    By productSection = By.cssSelector("section#productSection");

    @FindBy
    By productList = By.cssSelector("ul.product-container li.card__product");

    @FindBy
    By nextButton = By.cssSelector("div.pagination > button#omni-next-click");

    @FindBy
    By fulfillmentModal = By.cssSelector("div.modal__content fieldset.fulfillment");

    @FindBy
    By addToCartModal = By.cssSelector("div.modal__content a#addToCart-cart-checkout");
    
    public SearchResultPage(WebDriver driver) {
        super(driver);
        // No assertions, throws an exception if the element is not loaded
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(d -> d.findElement(productSection));
    }

    // Returning a list of products is a service of the page
    public List<Product> getProducts() {
        return driver.findElements(productList)
            .stream()
            .map(e -> new Product(e)) // Map WebElement to a product component
            .collect(Collectors.toList());
    }

    public WebElement nextButton() {
        return driver.findElement(nextButton);        
    }

    public CartPage processProductToCart(String fulfillmentBy) {
        WebElement element = null;
        
        waitForElement(fulfillmentModal, 5);
        element = driver.findElement(fulfillmentModal);
        Fulfillment fulfillment = new Fulfillment(element);
        fulfillment.selectFulfillmentOption(fulfillmentBy).click();
        
        waitForElement(addToCartModal,5);
        driver.findElement(addToCartModal).click();
        
        return new CartPage(driver);
    }

    private void waitForElement(By by, long seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
            .until(d -> d.findElement(by));
    }

}
