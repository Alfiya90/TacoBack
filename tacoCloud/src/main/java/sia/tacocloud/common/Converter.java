package sia.tacocloud.common;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import sia.tacocloud.data.dto.IngredientsDTO;
import sia.tacocloud.data.dto.TacoDTO;
import sia.tacocloud.data.dto.TacoOrderDTO;
import sia.tacocloud.data.model.IngredientByIdConverter;
import sia.tacocloud.data.model.Ingredients;
import sia.tacocloud.data.model.Taco;
import sia.tacocloud.data.model.TacosOrder;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class Converter {
    public static Taco convert(TacoDTO tacoDTO, IngredientByIdConverter ingredientByIdConverter) {
        Taco taco = new Taco();

        BeanUtils.copyProperties(tacoDTO, taco);
        /*taco.setCreateAt(LocalDate.now());*/
        taco.setIngredients(tacoDTO.getIngredientsList().stream().map(ingredientByIdConverter::convert).collect(Collectors.toList()));
        return taco;
    }

    public static Ingredients convert(IngredientsDTO ingredientsDTO) {
        Ingredients ingredients = new Ingredients();
        BeanUtils.copyProperties(ingredientsDTO, ingredients);
        return  ingredients;
    }
    public static TacosOrder convert(TacoOrderDTO tacoOrderDTO, IngredientByIdConverter ingredientByIdConverter) {
        TacosOrder tacosOrder = new TacosOrder();
        BeanUtils.copyProperties(tacoOrderDTO, tacosOrder);
        /*tacosOrder.setPlacedAt(LocalDate.now());*/
        tacosOrder.setTacos(tacoOrderDTO.getTacos().stream().map(item -> {
            return  convert(item, ingredientByIdConverter);
        }).collect(Collectors.toList()));
        return  tacosOrder;
    }

}
