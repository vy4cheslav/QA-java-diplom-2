import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.NewUser;
import user.UserClient;
import user.UserParams;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.CoreMatchers.is;

public class OrderGetTest {
    private OrderClient orderClient;
    private UserClient userClient;
    private NewUser createdUser = UserParams.createdUser();
    private String userAccessToken;
    Order orderWithIngredients = Order.orderWithIngredients();

    @Before
    @Description("Авторизовываемся пользователем и создаем ему заказ")
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
        ValidatableResponse validatableResponse = userClient.authUser(createdUser);
        userAccessToken = validatableResponse.extract().path("accessToken");
    }

    @Test
    @DisplayName("Получаем список заказов пользователя")
    public void gettingOrdersList() {
        orderClient.createOrder(orderWithIngredients, userAccessToken);
        ValidatableResponse validatableResponse = orderClient.getOrdersListWithAuth(userAccessToken).log().all();
        Assert.assertEquals(true, validatableResponse.extract().path("success"));
    }

    @Test
    @DisplayName("Получить список заказов без авторизации")
    public void getOrdersListWithoutAuth() {
        orderClient.getOrdersListWithoutAuth().assertThat().statusCode(SC_UNAUTHORIZED).body("success", is(false));

    }
}
