package com.ambrygen.that.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {

    @FindBy
    By mainContent = By.cssSelector("main#page-content");

    @FindBy
    By searchBarInput = By.cssSelector("form.input__search-contain > input");

    @FindBy
    By searchButton = By.cssSelector("form.input__search-contain > button.icon__search");

    public HomePage(WebDriver driver) {
        super(driver);
        // No assertions, throws an exception if the element is not loaded
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(d -> d.findElement(mainContent));
    }

    public SearchResultPage searchProduct(String searchString) {
        WebElement searchBar = driver.findElement(searchBarInput);
        searchBar.sendKeys(searchString);

        WebElement searchIcon = driver.findElement(searchButton);
        searchIcon.click();

        return new SearchResultPage(driver);

    }
}
