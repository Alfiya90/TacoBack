package sia.tacocloud.data.model;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import sia.tacocloud.dao.IngredientRepository;
import sia.tacocloud.dao.IngredientsDao;
import sia.tacocloud.dao.JDBCIngredientRepository;

import java.util.HashMap;
import java.util.Map;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class IngredientByIdConverter implements Converter<String, Ingredients> {
 private IngredientRepository ingredientRepository;
 IngredientsDao ingredientsDao;


    @Autowired
    public IngredientByIdConverter (IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    };

    public IngredientByIdConverter (IngredientsDao ingredientsDao) {
        this.ingredientsDao = ingredientsDao;
    }


    @Override
    public Ingredients convert(String id) {
        // поск ингредиентов через БД JDBCTemplate
        return ingredientRepository.findById(id).orElse(null);

        // поиск через БД (PrepareStatement)
        // return ingredientsDao.findIngredient(id);

    }
}
