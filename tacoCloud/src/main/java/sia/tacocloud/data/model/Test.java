package sia.tacocloud.data.model;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<Ingredients> ingredients = Arrays.asList(
                new Ingredients("FLTO"),
                new Ingredients("COTO"),
                new Ingredients("GRBF"),
                new Ingredients("CARN"),
                new Ingredients("TMTO"),
                new Ingredients("LETC"),
                new Ingredients("CHED"),
                new Ingredients("JACK"),
                new Ingredients("SLSA"),
                new Ingredients("SRCR")
        );
        System.out.println(ingredients);
    }
}
