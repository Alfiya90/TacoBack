package sia.tacocloud.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import sia.tacocloud.data.model.Ingredients;

import java.util.Optional;
@Repository
@RepositoryRestResource(collectionResourceRel = "ingredients", path = "ingredients")
public interface IngredientRepository extends CrudRepository <Ingredients, String> {

    Iterable <Ingredients> findAll();

    Optional<Ingredients> findById(String id);

    Ingredients save (Ingredients ingredients);
}
