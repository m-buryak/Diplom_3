import diplom.API.client.UserClient;
import diplom.API.steps.UserSteps;
import diplom.pageObjects.LoginPage;
import diplom.pageObjects.MainPage;
import diplom.pageObjects.RegistrationPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

public class RegistrationTest {

    private static WebDriver driver;

    private static MainPage mainPage;
    private static LoginPage loginPage;
    private static RegistrationPage registrationPage;

    private static UserSteps userSteps;

    private static List<String> accessTokens = new ArrayList<>();
    @BeforeClass
    public static void setup() {
        switch (String.valueOf(System.getProperty("browser"))) {
            case "firefox":
                ChromeOptions options = new ChromeOptions();
                options.setBinary("/src/test/resources/yandexdriver/exe");
                driver = new ChromeDriver(options);
                driver = new FirefoxDriver();
                mainPage = new MainPage(driver);
                loginPage = new LoginPage(driver);
                registrationPage = new RegistrationPage(driver);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().clearDriverCache().setup();;
                driver = new ChromeDriver();
                mainPage = new MainPage(driver);
                loginPage = new LoginPage(driver);
                registrationPage = new RegistrationPage(driver);
        }
    }

    @Before
    public void setupAPI() {
        userSteps = new UserSteps(new UserClient());
    }

    @Test
    public void successfulRegistrationTest() throws InterruptedException {
        String email = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@" + RandomStringUtils.randomAlphabetic(6).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(3).toLowerCase();
        String name = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);

        mainPage.openMainPage();
        mainPage.clickOnLoginButton();
        loginPage.clickOnRegistrationLink();
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        registrationPage.clickOnRegistrationButton();

        Assert.assertTrue(loginPage.isDisplayLoginHeader());

    }


    @Test
    public void registrationWithIncorrectPasswordTest() {
        String email = RandomStringUtils.randomAlphabetic(10).toLowerCase() + "@" + RandomStringUtils.randomAlphabetic(6).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(3).toLowerCase();
        String name = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(5);

        mainPage.openMainPage();
        mainPage.clickOnLoginButton();
        loginPage.clickOnRegistrationLink();
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        registrationPage.clickOnRegistrationButton();

        Assert.assertTrue(registrationPage.isDisplayIncorrectPasswordMessage());
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
