import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import user.NewUser;
import user.UserClient;
import user.UserParams;

import static config.Constants.ERROR_SHOULD_BE_AUTH;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;

public class UserEditTest {
    private UserClient userClient;
    private String userAccessToken;
    private NewUser user = UserParams.randomUser();
    private NewUser newUserData = UserParams.randomUser();
    private String emailAfterChanges;
    private String nameAfterChanges;

    @Before
    @Description("Регистрируем пользователя и получаем токен")
    public void setUp() {
        userClient = new UserClient();
        userClient.creatingUser(user);
        userAccessToken = userClient.authUser(user).extract().path("accessToken");
    }

    @Test
    @DisplayName("Смена данных у авторизованного пользователя")
    public void changeUserInfo() {
        ValidatableResponse validatableResponse = userClient.changeUser(userAccessToken, newUserData).assertThat().statusCode(SC_OK).body("success", is(true)).log().all();
        emailAfterChanges = validatableResponse.extract().path("user.email");
        nameAfterChanges = validatableResponse.extract().path("user.name");
        Assert.assertThat(true, is(equalsIgnoreCase(newUserData.getEmail(), emailAfterChanges)));
        Assert.assertEquals(newUserData.getName(), nameAfterChanges);
    }
    @Test
    @DisplayName("Сменить данные без авторизации")
    public void editUserInfoWithoutAuth() {
        userClient.changeUserWithoutAuth(newUserData).assertThat().statusCode(SC_UNAUTHORIZED).body("message", is(ERROR_SHOULD_BE_AUTH));
    }

    @After
    @Description("Удаляем созданного пользователя")
    public void deleteCreatedUser() {
        userClient.deleteUser(userAccessToken).assertThat().statusCode(SC_ACCEPTED);
    }
}
