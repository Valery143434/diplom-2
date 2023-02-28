import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class CreateUserTest {
    private Response response;
    private User user;
    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("создание пользователя")
    public void createUserTest() {
        user = User.dataForCreateUser();
        response = userClient.createUser(user);
        response.then().assertThat().statusCode(200).and().body("accessToken", notNullValue());
    }

    @Test
    @DisplayName("проверка получения ошибки при создании одного пользователя, дважды")
    public void createUserTwiceTest() {
        user = User.getExistUser();
        response = userClient.createUser(user);
        response.then().assertThat().statusCode(403).and()
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("проверка получения ошибки при создании пользователя без пароля")
    public void createUserWithoutPasswordTest() {
        user = User.createUserWithoutPassword();
        response = userClient.createUser(user);
        response.then().assertThat().statusCode(403).and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("проверка получения ошибки при создании пользователя без почты")
    public void createUserWithoutEmailTest() {
        user = User.createUserWithoutEmail();
        response = userClient.createUser(user);
        response.then().assertThat().statusCode(403).and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("проверка получения ошибки при создании пользователя без имени")
    public void createUserWithoutNameTest() {
        user = User.createUserWithoutName();
        response = userClient.createUser(user);
        response.then().assertThat().statusCode(403).and()
                .body("message", equalTo("Email, password and name are required fields"));
    }
    @After
    public void delete() {
        if(response.getStatusCode() == 200){
            userClient.deleteUser(response.then().extract().path("accessToken"));
        }
    }
}
