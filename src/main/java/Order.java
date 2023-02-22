import java.util.ArrayList;
import java.util.List;
public class Order {
    private List<String> ingredients = new ArrayList<>();
    public static Order getOrder() {
        return new Order(List.of("1", "2", "3"));
    }

    public static Order getOrderEmpty() {
        return new Order(List.of());
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    public static Order getOrderCorrect() {
        return new Order(List.of("61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa72"));
    }
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
