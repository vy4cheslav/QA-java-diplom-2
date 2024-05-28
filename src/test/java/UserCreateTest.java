import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;

import org.junit.After;
import org.junit.Test;
import user.NewUser;
import user.UserClient;
import user.UserParams;

import static config.Constants.ERROR_NOT_ENOUGH_DATA;
import static config.Constants.ERROR_USER_EXIST;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;

public class UserCreateTest {

    private UserClient userClient = new UserClient();
    private NewUser randomNewUser = UserParams.randomUser();
    private String userAccessToken;
    private boolean isCreated = false;

    @Test
    @DisplayName("Регистрация с рандомными валидными данными")
    public void userCreating() {
        userClient.creatingUser(randomNewUser)
                .assertThat()
                .statusCode(SC_OK)
                .body("success", is(true));
        isCreated = true;
    }

    @Test
    @DisplayName("Регистрация двух юзеров с одинаковыми данными")
    public void createTwoSameUsers() {
        userClient.creatingUser(randomNewUser).assertThat().statusCode(SC_OK);
        userClient.creatingUser(randomNewUser).assertThat().statusCode(SC_FORBIDDEN).body("message", is(ERROR_USER_EXIST));
        isCreated = true;
    }

    @Test
    @DisplayName("Регистрация без пароля")
    public void createUserWithoutPassword() {
        randomNewUser.setPassword(null);
        userClient.creatingUser(randomNewUser).assertThat().statusCode(SC_FORBIDDEN).body("message", is(ERROR_NOT_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Регистрация без email")
    public void createUserWithoutEmail() {
        randomNewUser.setEmail(null);
        userClient.creatingUser(randomNewUser).assertThat().statusCode(SC_FORBIDDEN).body("message", is(ERROR_NOT_ENOUGH_DATA));
    }

    @Test
    @DisplayName("Регистрации без имени")
    public void createUserWithoutName() {
        randomNewUser.setName(null);
        userClient.creatingUser(randomNewUser).assertThat().statusCode(SC_FORBIDDEN).body("message", is(ERROR_NOT_ENOUGH_DATA));
    }

    @After
    @Description("Удаление пользователя")
    public void deleteCreatedUser() {
        if (isCreated) {
            ValidatableResponse validatableResponse = userClient.authUser(randomNewUser);
            userAccessToken = validatableResponse.extract().path("accessToken");
            userClient.deleteUser(userAccessToken).assertThat().statusCode(SC_ACCEPTED);
        }
    }
}
