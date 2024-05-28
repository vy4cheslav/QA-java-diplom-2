import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.NewUser;
import user.UserClient;
import user.UserParams;

import static config.Constants.ERROR_WRONG_PASSWORD;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;

public class UserAuthTest {
    private UserClient userClient;
    private String userAccessToken;
    private NewUser notRegUser = UserParams.randomUser();
    private NewUser randomNewUser = UserParams.randomUser();
    private NewUser wrongPasswordUser = UserParams.wrongPasswordUser();


    @Before
    @Description("Создаем нового пользователя и получаем токен")
    public void setUp() {
        userClient = new UserClient();
        userClient.creatingUser(randomNewUser);
        userAccessToken = userClient.authUser(randomNewUser).extract().path("accessToken");
    }
    @Test
    @DisplayName("Проверяем авторизацию c корректными данными")
    public void authUser() {
        userClient.authUser(randomNewUser).assertThat().statusCode(SC_OK);
    }

    @Test
    @DisplayName("Авторизация с неверными данными")
    public void authWithWrongData() {
        userClient.authUser(notRegUser).assertThat().statusCode(SC_UNAUTHORIZED).body("message", is(ERROR_WRONG_PASSWORD));
    }
    @Test
    @DisplayName("Авторизация с не корректным паролем")
    public void authWithWrongPassword() {
        userClient.authUser(wrongPasswordUser).assertThat().statusCode(SC_UNAUTHORIZED).body("message", is(ERROR_WRONG_PASSWORD));
    }
    @After
    @Description("Удаляем созданного пользователя")
    public void deleteCreatedUser() {
        userClient.deleteUser(userAccessToken).assertThat().statusCode(SC_ACCEPTED);
    }
}
