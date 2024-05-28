import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.NewUser;
import user.UserClient;
import user.UserParams;

import static config.Constants.ERROR_INGREDIENTS;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;

public class OrderCreateTest {
    Order orderWithIngredients = Order.orderWithIngredients();
    Order orderWithWrongIngredients = Order.orderWithWrongIngredients();
    private OrderClient orderClient = new OrderClient();
    private UserClient userClient = new UserClient();
    private NewUser randomNewUser = UserParams.randomUser();
    private String accessToken;
    Order nullOrder = new Order();

    @Before
    public void setUp(){
        userClient.creatingUser(randomNewUser);
        ValidatableResponse validatableResponse = userClient.authUser(randomNewUser);
        accessToken = validatableResponse.extract().path("accessToken");
    }
    @Test
    @DisplayName("Создание заказа с ингридиентами")
    public void createOrderWithIngredients(){
        ValidatableResponse validatableResponse = orderClient.createOrder(orderWithIngredients,accessToken);
        validatableResponse.assertThat().statusCode(SC_OK).body("success", is(true));
    }

    @Test
    @DisplayName("Создание заказа без ингридиентов")
    public void createOrderWithoutIngredients(){
        ValidatableResponse validatableResponse = orderClient.createOrder(nullOrder,accessToken);
        validatableResponse.assertThat().statusCode(SC_BAD_REQUEST).body("message", is(ERROR_INGREDIENTS));
    }
    @Test
    @DisplayName("Создание заказа с неверным хэшем ингридиентов")
    public void createOrderWithWrongIngredients(){
        ValidatableResponse validatableResponse = orderClient.createOrder(orderWithWrongIngredients, accessToken);
        validatableResponse.assertThat().statusCode(SC_INTERNAL_SERVER_ERROR);

    }
    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuth(){
        ValidatableResponse validatableResponse = orderClient.createOrderWithoutAuth(orderWithIngredients);
        validatableResponse.assertThat().statusCode(SC_UNAUTHORIZED).body("success", is(false));
    }
    @After
    public void clean(){
        userClient.deleteUser(accessToken);
    }

}
