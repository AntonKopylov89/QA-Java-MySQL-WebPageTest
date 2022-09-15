package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class LoginPage {

    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        loginField.setValue(info.getTestUserLogin());
        passwordField.setValue(info.getTestUserPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void inValidPassword(DataHelper.InvalidAuthInfo info) {
        loginField.setValue(info.getUserLogin());
        passwordField.setValue(info.getUserPassword());
        loginButton.click();
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Ошибка! " + "Неверно указан логин или пароль"));
    }

    public void cleanLoginField() {
        loginField.sendKeys(Keys.CONTROL + "A");
        loginField.sendKeys(BACK_SPACE);
    }

    public void cleanPasswordField() {
        passwordField.sendKeys(Keys.CONTROL + "A");
        passwordField.sendKeys(BACK_SPACE);
    }

    public void enterInvalidPasswordAndCleanFields(DataHelper.InvalidAuthInfo info) {
        inValidPassword(info);
        cleanLoginField();
        cleanPasswordField();
    }

    public void blockSystem(DataHelper.InvalidAuthInfo info) {
        enterInvalidPasswordAndCleanFields(info);
        enterInvalidPasswordAndCleanFields(info);
        inValidPassword(info);
        $("[data-test-id=error-notification]").shouldHave(Condition.text("Система заблокирована"));
    }

}
