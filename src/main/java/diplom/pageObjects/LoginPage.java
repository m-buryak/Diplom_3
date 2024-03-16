package diplom.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    private final By registrationLink = By.xpath(".//a[text()='Зарегистрироваться']");

    private final By loginHeader = By.xpath("//h2[text()='Вход']");
    private final By loginButton = By.xpath("//button[text()='Войти']");
    private final By emailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By forgotPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public void clickOnRegistrationLink() {
        driver.findElement(registrationLink).click();
    }

    @Step
    public boolean isDisplayLoginHeader() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(loginHeader));
        return driver.findElement(loginHeader).isDisplayed();
    }

    @Step
    public void clickOnLoginButton() {
        driver.findElement(loginButton).click();
    }

    @Step
    public void setEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    @Step
    public void setPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    @Step
    public void clickOnForgotPasswordLink() {
        driver.findElement(forgotPasswordLink).click();
    }
}
