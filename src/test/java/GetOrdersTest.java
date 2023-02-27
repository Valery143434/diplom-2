import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
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
    @DisplayName("Получение заказа авторизованным пользователем")
    public void getOrdersWithLoginTest() {
//        User user = User.getExistUser();
        response = userClient.loginUser(User.getExistUser());
        token = response.then().extract().body().path("accessToken");
        order = Order.getIngredientsForOrders();
        response = orderClient.createOrder(order, token);
        response = orderClient.getOrders(token);
        response.then().assertThat().statusCode(200).and().body("orders", notNullValue());
    }

    @Test
    @DisplayName("проверка возврата ошибки при получение заказа неавторизованным пользователем")
    public void getOrdersWithoutLoginTest() {
        order = Order.getIngredientsForOrders();
        response = orderClient.createOrder(order, "token");
        response = orderClient.getOrders("token");
        response.then().assertThat().statusCode(401).and()
                .body("message", equalTo("You should be authorised"));
    }
}
