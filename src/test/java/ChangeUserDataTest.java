
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import static org.hamcrest.Matchers.*;

public class ChangeUserDataTest {
    private Response response;
    private final UserClient userClient = new UserClient();
    private String token;
    private User user;

    @Before
    public void before() {
        user = User.dataForCreateUser();
        response = userClient.createUser(user);
        token = response.then().extract().body().path("accessToken");
    }

    @Test
    @DisplayName("смена пароля с авторизацией")
    public void updatePasswordWithLoginTest() {
        user.setPassword("password123");
        response = userClient.updateUser(user, token);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("проверка получения ошибки при смене пароля без авторизации")
    public void updatePasswordWithoutLoginTest() {
        user.setPassword("password123");
        response = userClient.updateUser(user, "null");
        response.then().assertThat().statusCode(401).and().body("success", equalTo(false));
    }

    @Test
    @DisplayName("смена имени и почты с авторизацией")
    public void updateEmailAndNameWithLoginTest() {
        user.setEmail("NewEmail@back.ru");
        user.setName("Emmett");
        response = userClient.updateUser(user, token);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("проверка получения ошибки при смене имени и почты без авторизации")
    public void updateEmailAndNameWithoutLoginTest() {
        user.setEmail("NewEmail@back.ru");
        user.setName("Emmett");
        response = userClient.updateUser(user, "null");
        response.then().assertThat().statusCode(401).and().body("success", equalTo(false));
    }

    @After
    public void delete() {
        userClient.deleteUser(token);
    }
}

