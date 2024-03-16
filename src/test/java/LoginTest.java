import diplom.API.client.UserClient;
import diplom.API.steps.UserSteps;
import diplom.pageObjects.ForgotPasswordPage;
import diplom.pageObjects.LoginPage;
import diplom.pageObjects.MainPage;
import diplom.pageObjects.RegistrationPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class LoginTest {
    private static WebDriver driver;

    private static MainPage mainPage;
    private static LoginPage loginPage;
    private static RegistrationPage registrationPage;
    private static ForgotPasswordPage forgotPasswordPage;

    private static UserSteps userSteps;

    private static List<String> accessTokens = new ArrayList<>();
    @BeforeClass
    public static void setup() {
        switch (String.valueOf(System.getProperty("browser"))) {
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/yandexdriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setBrowserVersion("120");
                driver = new ChromeDriver(options);
                mainPage = new MainPage(driver);
                loginPage = new LoginPage(driver);
                registrationPage = new RegistrationPage(driver);
                forgotPasswordPage = new ForgotPasswordPage(driver);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().clearDriverCache().setup();
                driver = new ChromeDriver();
                mainPage = new MainPage(driver);
                loginPage = new LoginPage(driver);
                registrationPage = new RegistrationPage(driver);
                forgotPasswordPage = new ForgotPasswordPage(driver);
        }
    }

    @Before
    public void setupAPI() {
        userSteps = new UserSteps(new UserClient());
    }

    @Test
    public void loginFromLoginButtonOnMainPage() {
        String email = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@" + RandomStringUtils.randomAlphabetic(6).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(3).toLowerCase();
        String name = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        userSteps.create(email, name, password);

        mainPage.openMainPage();
        mainPage.clickOnLoginButton();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickOnLoginButton();
        Assert.assertTrue(mainPage.isDisplayedPlaceOrderButton());

        String accessToken = userSteps.login(email, password)
                .extract().path("accessToken");

        accessTokens.add(accessToken);

    }

    @Test
    public void loginFromPersonalAreaButtonOnMainPage() {
        String email = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@" + RandomStringUtils.randomAlphabetic(6).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(3).toLowerCase();
        String name = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        userSteps.create(email, name, password);

        mainPage.openMainPage();
        mainPage.clickOnPersonalAreaButton();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickOnLoginButton();
        Assert.assertTrue(mainPage.isDisplayedPlaceOrderButton());

        String accessToken = userSteps.login(email, password)
                .extract().path("accessToken");

        accessTokens.add(accessToken);

    }

    @Test
    public void loginFromLoginLinkOnRegistrationPage() {
        String email = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@" + RandomStringUtils.randomAlphabetic(6).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(3).toLowerCase();
        String name = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        userSteps.create(email, name, password);

        mainPage.openMainPage();
        mainPage.clickOnLoginButton();
        loginPage.clickOnRegistrationLink();
        registrationPage.clickOnLoginLink();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickOnLoginButton();
        Assert.assertTrue(mainPage.isDisplayedPlaceOrderButton());

        String accessToken = userSteps.login(email, password)
                .extract().path("accessToken");

        accessTokens.add(accessToken);
    }

    @Test
    public void loginFromLoginLinkOnForgotPasswordPage() {
        String email = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@" + RandomStringUtils.randomAlphabetic(6).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(3).toLowerCase();
        String name = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        userSteps.create(email, name, password);

        mainPage.openMainPage();
        mainPage.clickOnPersonalAreaButton();
        loginPage.clickOnForgotPasswordLink();
        forgotPasswordPage.clickOnLoginLink();
        loginPage.setEmail(email);
        loginPage.setPassword(password);
        loginPage.clickOnLoginButton();
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
