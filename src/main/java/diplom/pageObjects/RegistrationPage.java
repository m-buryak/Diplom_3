package diplom.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    private final WebDriver driver;
    private final By nameInput = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath(".//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath(".//label[text()='Пароль']/following-sibling::input");
    private final By registrationButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By incorrectPasswordMessage = By.xpath("//p[text()='Некорректный пароль']");
    private final By loginLink = By.xpath(".//a[text()='Войти']");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public void setName(String name) {
        driver.findElement(nameInput).sendKeys(name);
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
    public void clickOnRegistrationButton() {
        driver.findElement(registrationButton).click();
    }

    @Step
    public boolean isDisplayIncorrectPasswordMessage() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(incorrectPasswordMessage));
        return driver.findElement(incorrectPasswordMessage).isDisplayed();
    }

    @Step
    public void clickOnLoginLink() {
        driver.findElement(loginLink).click();
    }
}
