import diplom.API.client.UserClient;
import diplom.API.steps.UserSteps;
import diplom.pageObjects.LoginPage;
import diplom.pageObjects.MainPage;
import diplom.pageObjects.PersonalAreaPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class LogoutTest {
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
                System.setProperty("webdriver.chrome.driver", "src/test/resources/yandexdriver.exe");
                ChromeOptions options = new ChromeOptions();
                options.setBrowserVersion("120");
                driver = new ChromeDriver(options);
                mainPage = new MainPage(driver);
                loginPage = new LoginPage(driver);
                personalAreaPage = new PersonalAreaPage(driver);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().clearDriverCache().setup();
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
    public void logoutFromPersonalTest() {
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
        personalAreaPage.clickOnLogoutButton();
        Assert.assertTrue(loginPage.isDisplayLoginHeader());

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
