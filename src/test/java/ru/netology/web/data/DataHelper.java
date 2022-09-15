package ru.netology.web.data;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DataHelper {


    @Value
    public static class AuthInfo {
        private String id;
        private String testUserLogin;
        private String testUserPassword;
    }

    @Value
    public static class InvalidAuthInfo {
        private String UserId;
        private String UserLogin;
        private String UserPassword;
    }

    private static String getGeneratePassword() {
        return new Faker().internet().password();
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo(getDataBaseId("vasya"), "vasya", "qwerty123");
    }

    public static InvalidAuthInfo getInvalidAuthInfo() {
        return new InvalidAuthInfo(getDataBaseId("vasya"), "vasya", getGeneratePassword());
    }

    private DataHelper() {
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static String getVerificationCodeFor(AuthInfo authInfo) {
        return verificationCode(authInfo);
    }

    @SneakyThrows
    private static String verificationCode(AuthInfo user) {
        Thread.sleep(500);
        var runner = new QueryRunner();
        var sqlRequestSortByTime = "SELECT code FROM auth_codes WHERE user_id = ? ORDER BY created DESC";

        try (
                var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
        ) {
            return runner.query(conn, sqlRequestSortByTime, user.getId(), new ScalarHandler<>());
        }
    }

    @SneakyThrows
    private static String getDataBaseId(String login) {
        var runner = new QueryRunner();

        var sqlRequestTakeUserId = "SELECT id FROM users WHERE login = ?";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );

        ) {
            return runner.query(conn, sqlRequestTakeUserId, login, new ScalarHandler<>());
        }
    }
}
