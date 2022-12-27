package sia.tacocloud.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data

public class IngredientRef {
    private long tacoId;
    private  String ingredientsId;
}
