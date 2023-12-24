package workflow;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

public class AddToCartTest {

    static WebDriver driver= null;

    @BeforeTest
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--deny-permission-prompts");
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().deleteAllCookies();
    }

    @Test
    //@Parameters({"productName", "quantity"}) String productName, int quantity
    public void verifyAddToCartWorkflow() {

        String searchString = "colgate";

        driver.get("https://www.walgreens.com/");

        Assert.assertTrue(driver.getTitle().contains("Walgreens"));

        System.out.println("Title found to be: " + driver.getTitle());

        WebElement searchBar = driver.findElement(By.cssSelector("form.input__search-contain > input"));
        searchBar.sendKeys(searchString);
        
        WebElement searchIcon = driver.findElement(By.cssSelector("form.input__search-contain > button.icon__search"));
        searchIcon.click();

        Assert.assertTrue(driver.getTitle().contains(searchString));

        List<List<WebElement>> pages = Lists.newArrayList();

        List<WebElement> products = driver.findElements(By.cssSelector("ul.product-container > li.card__product button.btn__blue"));
        System.out.println(products.size());
        pages.add(products);

        WebElement nextPageButton = driver.findElement(By.cssSelector("div.pagination > button#omni-next-click"));
        boolean isDisabled = isAttributePresent(nextPageButton, "disabled");

        while (isDisabled) {
            nextPageButton.click();
            // Wait for page to load
            isDisabled = isAttributePresent(nextPageButton, "disabled");
            products = driver.findElements(By.cssSelector("ul.product-container > li.card__product button.btn__blue"));
            pages.add(products);
        }

        System.out.println(pages.size());


    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }

    private boolean isAttributePresent(WebElement element, String attribute) {
        Boolean result = false;
        try {
            String value = element.getAttribute(attribute);
            if ((value != null) || (value != "")) {
                result = true;
            }
        } catch (Exception e) {}
    
        return result;
    }
}
