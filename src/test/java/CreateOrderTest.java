import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
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
        User user = User.getExistUser();
        response = userClient.loginUser(user);
        token = response.then().extract().body().path("accessToken");
        response = orderClient.createOrder(Order.getIngredientsForOrders(), token);
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("создание заказа без авторизации")
    public void createOrderWithoutLoginTest() {
        response = orderClient.createOrder(Order.getIngredientsForOrders(), "token");
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("создание заказа с ингредиентами")
    public void createOrderWithIngredientsTest() {
        order = Order.getIngredientsForOrders();
        response = orderClient.createOrder(order, "token");
        response.then().assertThat().statusCode(200).and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("проверка получения ошибки при создании заказа без ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        response = orderClient.createOrder(Order.getOrderEmpty(), "token");
        response.then().assertThat().statusCode(400).and()
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("проверка получения ошибки при создании заказа с неверным хешем ингредиентов")
    public void createOrderWithoutValidHashTest() {
        order = Order.getOrderWrongHash();
        response = orderClient.createOrder(order, "token");
        response.then().assertThat().statusCode(500);
    }
}
