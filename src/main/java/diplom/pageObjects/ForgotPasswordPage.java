package diplom.pageObjects;

import io.qameta.allure.Step;
import jdk.jfr.Registered;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {
    public final WebDriver driver;
    public final By loginLink = By.xpath(".//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step
    public void clickOnLoginLink() {
        driver.findElement(loginLink).click();
    }
}
