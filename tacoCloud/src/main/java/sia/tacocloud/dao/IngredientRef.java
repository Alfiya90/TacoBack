package sia.tacocloud.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class IngredientRef {
    private long tacoId;
    private  String ingredientId;
}
