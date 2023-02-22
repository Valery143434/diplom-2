import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
public class LoginTest {
    private Response response;
    private User user;
    private final UserClient userClient = new UserClient();
    private String email;
    private String password;
    private String token;
    @Before
    public void before() {
        user = User.createUser();
        response = userClient.createUser(user);
        token = response
                .then()
                .extract()
                .body()
                .path("accessToken");
    }

    @Test
    public void loginRealUserTest() {
        response = userClient.loginUser(user = User.getExistUser());
        response
                .then()
                .statusCode(200)
                .assertThat()
                .body("success", equalTo(true));
    }
    @Test
    public void loginWithoutCorrectPasswordAndEmailTest() {
        email = user.getEmail();
        user.setEmail("Email");
        password = user.getPassword();
        user.setPassword("Password");
        response = userClient.loginUser(user, token);
        user.setEmail(email);
        user.setPassword(password);
        response
                .then()
                .assertThat()
                .body("success", equalTo(false))
                .and()
                .statusCode(401);
    }
    @After
    public void delete() {
        userClient.deleteUser(token);
    }
}
