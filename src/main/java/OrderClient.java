
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
public class OrderClient {
    public Response createOrder(Order order, String token) {
        return given().header("Content-Type", "application/json")
                .header("Authorization", token).baseUri(BaseURL.BASE_URL)
                .body(order).post(API.ORDERS_API);
    }
    public Response getOrders(String token) {
        return given().header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(BaseURL.BASE_URL).get(API.ORDERS_API);
    }
}
