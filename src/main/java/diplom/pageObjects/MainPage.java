package diplom.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private final WebDriver driver;
    private final String url = "https://stellarburgers.nomoreparties.site/";

    private final By loginButton = By.xpath(".//button[text()='Войти в аккаунт']");

    private final By placeOrderButton = By.xpath(".//button[text()='Оформить заказ']");
    private final By personalAreaButton = By.xpath((".//p[text()='Личный Кабинет']"));
    private final By bunTab = By.xpath(".//span[text()='Булки']/ancestor::div[contains(@class,'tab')]");
    private final By sauceTab = By.xpath(".//span[text()='Соусы']/ancestor::div[contains(@class,'tab')]");
    private final By toppingTab = By.xpath(".//span[text()='Начинки']/ancestor::div[contains(@class,'tab')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public void openMainPage() {
        driver.get(url);
    }

    @Step
    public void clickOnLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step
    public boolean isDisplayedPlaceOrderButton() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(placeOrderButton));
        return driver.findElement(placeOrderButton).isDisplayed();
    }

    @Step
    public void clickOnPersonalAreaButton() {
        driver.findElement(personalAreaButton).click();
    }

    @Step
    public void clickOnBunTab() {
        driver.findElement(bunTab).click();
    }

    @Step
    public void clickOnSauceTab() {
        driver.findElement(sauceTab).click();
    }

    @Step
    public void clickOnToppingTab() {
        driver.findElement(toppingTab).click();
    }

    @Step
    public boolean isActiveBunTab() {
        return driver.findElement(bunTab).getAttribute("class").contains("tab_tab_type_current");
    }

    @Step
    public boolean isActiveSauceTab() {
        return driver.findElement(sauceTab).getAttribute("class").contains("tab_tab_type_current");
    }

    @Step
    public boolean isActiveToppingTab() {
        return driver.findElement(toppingTab).getAttribute("class").contains("tab_tab_type_current");
    }
}
