package workflow;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import com.ambrygen.that.components.Product;
import com.ambrygen.that.pages.CartPage;
import com.ambrygen.that.pages.HomePage;
import com.ambrygen.that.pages.SearchResultPage;

public class AddToCartTest {

    static WebDriver driver = null;
    private HomePage homePage;
    private SearchResultPage searchResultPage;
    private CartPage cartPage;

    @BeforeTest
    public void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--deny-permission-prompts");
        
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().deleteAllCookies();

        driver.get("https://www.walgreens.com/");
    }

    @Test
    //@Parameters({"productName", "quantity"}) String productName, int quantity
    public void verifyAddToCartWorkflow() throws InterruptedException {

        String searchString = "colgate";
        int quantity = 5;

        homePage = new HomePage(driver);
        Assert.assertTrue(driver.getTitle().contains("Walgreens"));

        searchResultPage = homePage.searchProduct(searchString);
        Assert.assertTrue(driver.getTitle().contains(searchString));

        List<List<Product>> pages = Lists.newArrayList();
        List<Product> products = Lists.newArrayList();
        boolean hasNext = true;

        while(hasNext) {
            products = searchResultPage.getProducts();
            pages.add(products);
            if(searchResultPage.nextButton().isEnabled()) {
                searchResultPage.nextButton().click();
                Thread.sleep(5000);
            } else {
                hasNext = false;
            }
        }

        // Matches product title with search
        for (int i=0; i<pages.size(); i++) {
            for (Product product:products){
                Assert.assertTrue(product.getProductTitle().regionMatches(true, 0, searchString, 0, 0));
            }
        }
        
        // Add last product to cart and increment quantity
        Product lastProduct = pages.get(pages.size()-1).get(products.size()-1);
        lastProduct.addToCart().click();
        cartPage = searchResultPage.processProductToCart("pickup");

        BigDecimal productPrice = cartPage.getPrice();
        cartPage.incrementQuantity(quantity);
        BigDecimal actualPrice = cartPage.getPrice();
        BigDecimal expectedPrice = productPrice.multiply(BigDecimal.valueOf(quantity));

        Assert.assertEquals(actualPrice, expectedPrice);

    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }

}
