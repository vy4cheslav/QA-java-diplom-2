package order;

import config.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static config.Constants.*;
import static io.restassured.RestAssured.given;

public class OrderClient {
    @Step("Создание заказа с авторизацией")
    public ValidatableResponse createOrder(Order order, String accessToken) {
        return given()
                .spec(Client.spec())
                .body(order)
                .headers("Authorization", accessToken)
                .when()
                .post(ORDERS)
                .then();

    }

    @Step("Создание заказа без авторизации")
    public ValidatableResponse createOrderWithoutAuth(Order order) {
        return given()
                .spec(Client.spec())
                .body(order)
                .when()
                .post(ORDERS)
                .then();

    }

    @Step("Получить список заказов без авторизации")
    public ValidatableResponse getOrdersListWithoutAuth() {
        return given()
                .spec(Client.spec())
                .when()
                .get(ORDERS)
                .then();
    }

    @Step("Получить список заказов с авторизацией")
    public ValidatableResponse getOrdersListWithAuth(String accessToken) {
        return given()
                .spec(Client.spec())
                .headers("Authorization", accessToken)
                .when()
                .get(ORDERS)
                .then();

    }
}
