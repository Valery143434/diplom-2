import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;
public class UserClient {
    @Step("создаем пользователя")
    public Response createUser(User user) {
        return given().contentType(ContentType.JSON).baseUri(BaseURL.BASE_URL)
                .body(user).post(API.CREATE_API);
    }
    @Step("удаляем пользователя")
    public void deleteUser(String token) {
        given().contentType(ContentType.JSON).header("Authorization",
                token).baseUri(BaseURL.BASE_URL).delete(API.USER_API);
    }
    @Step("обновляем пользователя")
    public Response updateUser(User newUser, String token) {
        return given().contentType(ContentType.JSON).header("Authorization", token)
                .baseUri(BaseURL.BASE_URL).body(newUser).patch(API.USER_API);
    }
    @Step("используется авторизация без токена")
    public Response loginUser(User user) {
        return given().contentType(ContentType.JSON)
                .baseUri(BaseURL.BASE_URL).body(user).post(API.LOGIN_API);
    }
    @Step("используется авторизация с токеном")
    public Response loginUserWithToken(User user, String token) {
        return given().contentType(ContentType.JSON).header("Authorization", token)
                .baseUri(BaseURL.BASE_URL).body(user).post(API.LOGIN_API);
    }
}
