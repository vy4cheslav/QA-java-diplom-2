package order;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order {

    @Getter
    @Setter
    private List<String> ingredients;

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Order() {
        ingredients = new ArrayList<>();
    }

    public static Order orderWithIngredients() {
        return new Order(new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa71", "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa6d")));
    }

    public static Order orderWithWrongIngredients() {
        return new Order(new ArrayList<>(Arrays.asList("11113333aa", "88888888vvv", "876554433gggcf", "11113333aa")));
    }

}