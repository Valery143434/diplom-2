import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;
public class Order {
    private List<String> ingredients = new ArrayList<>();


    @Step("берем лист без индигриентов для заказа")
    public static Order getOrderEmpty() {
        return new Order(List.of());
    }

    public void setIngredientsSerill(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    @Step("берем лист с индигриентами для заказа")
    public static Order getIngredientsForOrders() {
        return new Order(List.of("61c0c5a71d1f82001bdaaa6f",
                "61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa72"));
    }
    @Step("берем список заказов с неверным хешем ингредиентов")
    public static Order getOrderWrongHash() {
        return new Order(List.of("1", "2", "3"));
    }
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
