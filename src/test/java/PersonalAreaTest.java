import diplom.API.client.UserClient;
import diplom.API.steps.UserSteps;
import diplom.pageObjects.LoginPage;
import diplom.pageObjects.MainPage;
import diplom.pageObjects.PersonalAreaPage;
import diplom.pageObjects.RegistrationPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

public class PersonalAreaTest {
    private static WebDriver driver;

    private static MainPage mainPage;
    private static LoginPage loginPage;
    private static PersonalAreaPage personalAreaPage;

    private static UserSteps userSteps;

    private static List<String> accessTokens = new ArrayList<>();
    @BeforeClass
    public static void setup() {
        switch (String.valueOf(System.getProperty("browser"))) {
            case "yandex":
                ChromeOptions options = new ChromeOptions();
                options.setBinary("/src/test/resources/yandexdriver/exe");
                driver = new ChromeDriver(options);
                mainPage = new MainPage(driver);
                loginPage = new LoginPage(driver);
                personalAreaPage = new PersonalAreaPage(driver);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().clearDriverCache().setup();;
                driver = new ChromeDriver();
                mainPage = new MainPage(driver);
                loginPage = new LoginPage(driver);
                personalAreaPage = new PersonalAreaPage(driver);

        }
    }

    @Before
    public void setupAPI() {
        userSteps = new UserSteps(new UserClient());
    }

    @Test
    public void enterPersonalAreaTest() {
        String email = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@" + RandomStringUtils.randomAlphabetic(6).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(3).toLowerCase();
        String name = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        userSteps.create(email, name, password);

        mainPage.openMainPage();
        mainPage.clickOnPersonalAreaButton();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickOnLoginButton();
        mainPage.clickOnPersonalAreaButton();
        Assert.assertEquals(name, personalAreaPage.getNameInputValue());
        Assert.assertEquals(email, personalAreaPage.getEmailInputValue());

        String accessToken = userSteps.login(email, password)
                .extract().path("accessToken");

        accessTokens.add(accessToken);
    }

    @Test
    public void enterConstructorFromPersonalAreaTest() {
        String email = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@" + RandomStringUtils.randomAlphabetic(6).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(3).toLowerCase();
        String name = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        userSteps.create(email, name, password);

        mainPage.openMainPage();
        mainPage.clickOnPersonalAreaButton();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickOnLoginButton();
        mainPage.clickOnPersonalAreaButton();
        personalAreaPage.clickOnConstructorButton();
        Assert.assertTrue(mainPage.isDisplayedPlaceOrderButton());

        String accessToken = userSteps.login(email, password)
                .extract().path("accessToken");

        accessTokens.add(accessToken);
    }

    @After
    public void deleteUsers() {
        for (String accessToken : accessTokens) {
            userSteps.delete(accessToken);
        }
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
}
