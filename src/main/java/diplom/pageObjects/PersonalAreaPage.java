package diplom.pageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PersonalAreaPage {
    private final WebDriver driver;
    private final By nameInput = By.xpath(".//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath(".//label[text()='Логин']/following-sibling::input");
    private final By logoutButton = By.xpath(".//button[text()='Выход']");
    private final By constructorButton = By.xpath(".//p[text()='Конструктор']");
    public PersonalAreaPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public String getNameInputValue() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        return driver.findElement(nameInput).getAttribute("value");
    }

    @Step
    public String getEmailInputValue() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        return driver.findElement(emailInput).getAttribute("value");
    }

    @Step
    public void clickOnLogoutButton() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
        driver.findElement(logoutButton).click();
    }

    @Step
    public void clickOnConstructorButton() {
        driver.findElement(constructorButton).click();
    }
}
