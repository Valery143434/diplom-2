import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class CreateOrderTest {
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private String token;
    private Order order;
    private Response response;
    @Test
    @DisplayName("создание заказа с авторизацией")
    public void createOrderWithLoginTest() {
        User user = User.createUser();
        response = userClient.createUser(user);
        token = response.then().extract().body().path("accessToken");
        response = orderClient.createOrder(Order.getOrderCorrect(), token);
        userClient.deleteUser(token);
        response
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }
    @Test
    @DisplayName("создание заказа без авторизации")
    public void createOrderWithoutLoginTest() {
        response = orderClient.createOrder(Order.getOrderCorrect(), "token");
        response
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }
    @Test
    @DisplayName("создание заказа с ингредиентами")
    public void createOrderWithIngredientsTest() {
        order = Order.getOrderCorrect();
        response = orderClient.createOrder(order, "token");
        response
                .then()
                .assertThat()
                .body("success", equalTo(true))
                .and()
                .statusCode(200);
    }
    @Test
    @DisplayName("создание заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        response = orderClient.createOrder(Order.getOrderEmpty(), "token");
        response
                .then()
                .assertThat()
                .body("message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(400);
    }
    @Test
    @DisplayName("создание заказа с неверным хешем ингредиентов")
    public void createOrderWithInvalidHashTest() {
        order = Order.getOrder();
        response = orderClient.createOrder(order, "token");
        response
                .then()
                .assertThat()
                .statusCode(500);
    }
}
