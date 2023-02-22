
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import static org.hamcrest.Matchers.*;

public class ChangeUserDataTest {
    private Response response;
    private String email;
    private String password;
    private String name;
    private final UserClient userClient = new UserClient();
    private String token;
    private User user;
    @Before
    public void before() {
        user = User.createUser();
        response = userClient.createUser(user);
        token = response.then().extract().body().path("accessToken");
    }
    @Test
    @DisplayName("смена пароля с авторизацией")
    public void shouldUpdatePassword() {
        password = user.getPassword();
        user.setPassword(password + "password");
        response = userClient.updateUser(user, token);
        user.setPassword(password);
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }
    @Test
    @DisplayName("смена пароля без авторизации")
    public void updatePasswordShouldBeError() {
        password = user.getPassword();
        user.setPassword(password + "password");
        response = userClient.updateUser(user, "null");
        user.setPassword(password);
        response.then().assertThat().body("success", equalTo(false))
                .and().statusCode(401);
    }
    @Test
    @DisplayName("смена имени и пароля с авторизацией")
    public void shouldUpdateEmailAndName() {
        email = user.getEmail();
        name = user.getName();
        user.setEmail(email + "email");
        user.setName(name + "name");
        response = userClient.updateUser(user, token);
        user.setEmail(email);
        user.setName(name);
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }
    @Test
    @DisplayName("смена имени и пароля без авторизации")
    public void updateEmailAndNameShouldBeError() {
        email = user.getEmail();
        name = user.getName();
        user.setEmail(email + "email");
        user.setName(name + "name");
        response = userClient.updateUser(user, "null");
        user.setEmail(email);
        user.setName(name);
        response.then().assertThat().body("success", equalTo(false))
                .and().statusCode(401);
    }
    @After
    public void delete() {
        userClient.deleteUser(token);
    }
}

