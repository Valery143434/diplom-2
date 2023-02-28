import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class LoginTest {
    private Response response;
    private User user;
    private final UserClient userClient = new UserClient();
    private String token;

    @Before
    public void before() {
        user = User.dataForCreateUser();
        response = userClient.createUser(user);
        token = response.then().extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Авторизация пользователя")
    public void loginRealUserTest() {
        response = userClient.loginUser(user);
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("проверка получения ошибки при авторизации пользователя с некорректным паролем")
    public void loginWithoutCorrectPasswordTest() {
        user.setPassword("Password");
        response = userClient.loginUserWithToken(user, token);
        response.then().assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }

    @Test
    @DisplayName("проверка получения ошибки при авторизации пользователя с некорректным логином")
    public void loginWithoutCorrectLoginTest() {
        user.setEmail("Email");
        response = userClient.loginUserWithToken(user, token);
        response.then().assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }

    @After
    public void delete() {
        userClient.deleteUser(token);
    }
}
