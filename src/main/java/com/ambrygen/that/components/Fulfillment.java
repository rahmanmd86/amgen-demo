package com.ambrygen.that.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Fulfillment extends BaseComponent {

    @FindBy
    By pickup = By.cssSelector("label[for='pickup-option']");

    @FindBy
    By sameday = By.cssSelector("label[for='same-day-option']");

    @FindBy
    By shipping = By.cssSelector("label[for='shipping-option']");    
    

    public Fulfillment(WebElement root) {
        super(root);
    }

    public WebElement selectFulfillmentOption(String option) {
        WebElement fulfillmentBy = null;
        switch (option) {
            case "pickup":
                fulfillmentBy = root.findElement(pickup);
                break;
            case "sameday":
                fulfillmentBy = root.findElement(sameday);
                break;
            case "shipping":
                fulfillmentBy = root.findElement(shipping);
                break;
            default:
                fulfillmentBy = root.findElement(pickup);
                break;
        }
        return fulfillmentBy;

    }
}
