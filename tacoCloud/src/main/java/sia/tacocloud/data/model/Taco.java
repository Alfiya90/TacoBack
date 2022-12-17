package sia.tacocloud.data.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;
import sia.tacocloud.dao.IngredientRef;

import java.time.LocalDate;
import java.util.List;

@Data
@Component
public class Taco {
    private Long id;
    @NotNull
    @Size(min = 5, message = "Name must be  at least 5 characters long")
    private String name;
    private LocalDate createAt;
    @NotNull
    @Size(min = 1, message = "You must choose at least 1 ingredient")
    private List<Ingredients> ingredients;

    public Taco() {
    }

    public Taco(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {

        this.name = name;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }
}
