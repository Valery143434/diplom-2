import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class CreateUserTest {
    private Response response;
    private User user;
    private final UserClient userClient = new UserClient();
    @Test
    @DisplayName("создание пользователя")
    public void createUsrTest() {
        user = User.createUser();
        response = userClient.createUser(user);
        String token = response
                .then()
                .extract()
                .body()
                .path("accessToken");
        userClient.deleteUser(token);
        response
                .then()
                .assertThat()
                .body("accessToken", notNullValue())
                .and()
                .statusCode(200);
    }
    @Test
    @DisplayName("создание пользователя, дважды")
    public void createUserTwiceTest() {
        user = User.getExistUser();
        response = userClient.createUser(user);
        response
                .then()
                .assertThat()
                .body("message", equalTo("User already exists"))
                .and()
                .statusCode(403);
    }
    @Test
    @DisplayName("создание пользователя без пароля")
    public void createUserWithoutPasswordTest() {
        user = User.createUserWithEmptyPassword();
        response = userClient.createUser(user);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }
    @Test
    @DisplayName("создание пользователя без почты")
    public void createUserWithoutEmailTest() {
        user = User.createUserWithEmptyEmail();
        response = userClient.createUser(user);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("создание пользователя без имени")
    public void createUserWithoutNameTest() {
        user = User.createUserWithEmptyName();
        response = userClient.createUser(user);
        response
                .then()
                .assertThat()
                .body("message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(403);
    }
}
