package sia.tacocloud.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import sia.tacocloud.data.model.Ingredients;

import java.util.Optional;
@Component
public interface IngredientRepository {
    Iterable <Ingredients> findAll();

    Optional<Ingredients> findById(String id);

    Ingredients save (Ingredients ingredients);
}
