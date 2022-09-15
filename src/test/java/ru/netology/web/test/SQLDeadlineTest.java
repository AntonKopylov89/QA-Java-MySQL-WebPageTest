package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.data.User;
import ru.netology.web.page.LoginPage;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class SQLDeadlineTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @AfterEach
    void closeWebBrowser() {
        closeWebDriver();
    }

    @Test
    void shouldSuccessfulAuthentication() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldUnsuccessfulAuthenticationWithInvalidPassword() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getInvalidAuthInfo();
        loginPage.inValidPassword(authInfo);
    }

    @Test
    void shouldBlockSystemWith3TimeUnsuccessfulAuthentication() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getInvalidAuthInfo();
        loginPage.blockSystem(authInfo);
    }
}
