import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
public class UserClient {
    public Response createUser(User user) {
        return given().contentType(ContentType.JSON).baseUri(BaseURL.BASE_URL)
                .body(user).post(API.CREATE_API);
    }
    public void deleteUser(String token) {
        given().contentType(ContentType.JSON).header("Authorization",
                token).baseUri(BaseURL.BASE_URL).delete(API.USER_API);
    }
    public Response updateUser(User newUser, String token) {
        return given().contentType(ContentType.JSON).header("Authorization", token)
                .baseUri(BaseURL.BASE_URL).body(newUser).patch(API.USER_API);
    }
    public Response loginUser(User user) {
        return given().contentType(ContentType.JSON)
                .baseUri(BaseURL.BASE_URL).body(user).post(API.LOGIN_API);
    }
    public Response loginUser(User user, String token) {
        return given().contentType(ContentType.JSON).header("Authorization", token)
                .baseUri(BaseURL.BASE_URL).body(user).post(API.LOGIN_API);
    }
}
