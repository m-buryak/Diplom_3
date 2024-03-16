import diplom.pageObjects.MainPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChangeTabsTest {
    private static WebDriver driver;

    private static MainPage mainPage;
    @BeforeClass
    public static void setup() {
        switch (String.valueOf(System.getProperty("browser"))) {
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/yandexdriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setBrowserVersion("120");
                driver = new ChromeDriver(options);
                mainPage = new MainPage(driver);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().clearDriverCache().setup();
                driver = new ChromeDriver();
                mainPage = new MainPage(driver);
        }
    }


    @Test
    public void sauceTabActiveTest() {
        mainPage.openMainPage();
        mainPage.clickOnSauceTab();
        Assert.assertTrue(mainPage.isActiveSauceTab());
        Assert.assertFalse(mainPage.isActiveBunTab());
        Assert.assertFalse(mainPage.isActiveToppingTab());
    }

    @Test
    public void bunTabActiveTest() {
        mainPage.openMainPage();
        mainPage.clickOnSauceTab();
        mainPage.clickOnBunTab();
        Assert.assertFalse(mainPage.isActiveSauceTab());
        Assert.assertTrue(mainPage.isActiveBunTab());
        Assert.assertFalse(mainPage.isActiveToppingTab());
    }

    @Test
    public void toppingTabActiveTest() {
        mainPage.openMainPage();
        mainPage.clickOnToppingTab();
        Assert.assertFalse(mainPage.isActiveSauceTab());
        Assert.assertFalse(mainPage.isActiveBunTab());
        Assert.assertTrue(mainPage.isActiveToppingTab());
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
