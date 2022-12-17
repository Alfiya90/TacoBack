package sia.tacocloud.dao;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import sia.tacocloud.data.model.Ingredients;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public  class JDBCIngredientRepository implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    public JDBCIngredientRepository (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    private Ingredients mapRowIngredient(ResultSet row, int romNum) throws SQLException {
            return new Ingredients(
                    row.getString("id"),
                    row.getString("name"),
                    Ingredients.Type.valueOf(row.getString("type"))
            );

        }
/*  private Ingredients mapRowIngredient(ResultSet row, int romNum) throws SQLException {
      Ingredients ingredient = new Ingredients();
      ingredient.setId(row.getString("id"));
      ingredient.setName(row.getString("name"));
      Ingredients.Type.valueOf(row.getString("type"));
      return ingredient;

  }*/


    @Override
    public Iterable<Ingredients> findAll() {
        return jdbcTemplate.query("SELECT id, name, type FROM ingredient", this :: mapRowIngredient);
    }



    @Override
    public Optional <Ingredients> findById(String id) {
        List<Ingredients> result = jdbcTemplate.query("SELECT id, name, type FROM ingredient where id = ?", this::mapRowIngredient, id);
        return result.size() == 0?
                Optional.empty(): Optional.of(result.get(0));
    }

    @Override
    public Ingredients save(Ingredients ingredients) {
        jdbcTemplate.update("INSERT INTO ingredient (id, name, type) values (?, ?, ?)",
                ingredients.getId(),
                ingredients.getName(),
                ingredients.getType().toString());
        return ingredients;
    }
}