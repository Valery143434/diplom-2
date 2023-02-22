import io.restassured.response.Response;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class GetOrdersTest {
    private Response response;
    private Order order;
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private String token;
    @Test
    public void getOrdersWithLoginTest() {
        User user = User.createUser();
        response = userClient.createUser(user);
        token = response
                .then()
                .extract()
                .body()
                .path("accessToken");
        order = Order.getOrderCorrect();
        response = orderClient.createOrder(order, token);
        response = orderClient.createOrder(order, token);
        response = orderClient.getOrders(token);
        userClient.deleteUser(token);
        response
                .then()
                .assertThat()
                .body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
    @Test
    public void getOrdersWithoutLoginTest() {
        order = Order.getOrderCorrect();
        response = orderClient.createOrder(order, "token");
        response = orderClient.createOrder(order, "token");
        response = orderClient.getOrders("token");
        response
                .then()
                .assertThat()
                .body("message", equalTo("You should be authorised"))
                .and()
                .statusCode(401);
    }
}
