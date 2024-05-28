package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import config.Client;
import org.junit.Assert;

import static config.Constants.*;
import static io.restassured.RestAssured.given;

public class UserClient {
    NewUser newUser;
    CreatedUser createdUser;

    @Step("Создание пользователя")
    public ValidatableResponse creatingUser(NewUser newUser) {
        return given()
                .spec(Client.spec())
                .body(newUser)
                .when()
                .post(CREATE_USER)
                .then();
    }
    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(Client.spec())
                .headers("Authorization", accessToken)
                .when()
                .delete(AUTH_USER)
                .then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse authUser(NewUser newUser) {
        createdUser = new CreatedUser(newUser.getEmail(), newUser.getPassword());
        return given()
                .spec(Client.spec())
                .body(createdUser)
                .when()
                .post(LOG_IN)
                .then();
    }

    @Step("Изменение данных пользователя")
    public ValidatableResponse changeUserWithoutAuth(NewUser newUser) {
        return given()
                .spec(Client.spec())
                .body(newUser)
                .when()
                .patch(AUTH_USER)
                .then();
    }
    @Step("Изменение данных пользователя")
    public ValidatableResponse changeUser(String accessToken, NewUser newUser) {
        return given()
                .spec(Client.spec())
                .body(newUser)
                .headers("Authorization", accessToken)
                .when()
                .patch(AUTH_USER)
                .then();
    }


}
