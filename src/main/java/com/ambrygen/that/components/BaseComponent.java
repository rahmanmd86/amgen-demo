package com.ambrygen.that.components;

import org.openqa.selenium.WebElement;

public abstract class BaseComponent {
    protected WebElement root;

    public BaseComponent(WebElement root) {
        this.root = root;
    }
}
