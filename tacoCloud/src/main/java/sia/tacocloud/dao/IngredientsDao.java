package sia.tacocloud.dao;
import org.springframework.stereotype.Component;
import sia.tacocloud.data.model.Ingredients;
import sia.tacocloud.data.model.Taco;
import sia.tacocloud.data.model.TacosOrder;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Component
public class IngredientsDao {
    private  static final String URL = "jdbc:mysql://localhost:3306/tacocloud";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "@l1990fi";
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private Connection getConnection() throws SQLException {
        return  DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public List<Ingredients> getIngredients() {
        List <Ingredients> ingredients = new ArrayList<>();
        try{
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM ingredients";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()){
                Ingredients ingredient = new Ingredients();
                ingredient.setId(resultSet.getString("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setType(Ingredients.Type.valueOf(resultSet.getString("type")));
                ingredients.add(ingredient);
            }
            statement.close();
            connection.close();

        }  catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  ingredients;
    }

    public Ingredients findIngredient(String id) {
        Ingredients ingredient = new Ingredients();
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ingredients WHERE id=?");
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            ingredient.setId(resultSet.getString("id"));
            ingredient.setName(resultSet.getString("name"));
            Ingredients.Type.valueOf(resultSet.getString("type"));

            preparedStatement.close();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  ingredient;
    }

    public TacosOrder saveOrder(TacosOrder order) {
        try{
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tacos_order (" +
                    "delivery_name, delivery_street, delivery_city, delivery_state, delivery_zip, " +
                    "cc_number, cc_expiration, cccvv, placed_at) values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            order.setPlacedAt(new java.util.Date());
            preparedStatement.setString(1, order.getDeliveryName());
            preparedStatement.setString(2, order.getDeliveryStreet());
            preparedStatement.setString(3, order.getDeliveryCity());
            preparedStatement.setString(4, order.getDeliveryState());
            preparedStatement.setString(5, order.getDeliveryZip());
            preparedStatement.setString(6, order.getCcNumber());
            preparedStatement.setString(7, order.getCcExpiration());
            preparedStatement.setString(8, order.getCcCVV());
            preparedStatement.setDate(9, new java.sql.Date(order.getPlacedAt().getTime()));

            int affectedRows = preparedStatement.executeUpdate();
            if( affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }


            try(ResultSet orderId = preparedStatement.getGeneratedKeys()) {
                if (orderId.next()) {
                    order.setId(orderId.getLong("id"));
                    List<Taco> tacos = order.getTacos();
                    for (Taco taco: tacos) {
                        saveTacoInDB(order.getId(), taco);
                    }
                }
            }
        } catch (SQLException throwables)
        {throwables.printStackTrace();
        }
        return order;
    }




    public long saveTacoInDB (Long orderId, Taco taco) {
        long tacoId = 0;
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO taco (id, create_at, name)  values (?, ?, ?)");
            preparedStatement.setLong(1, orderId);
            preparedStatement.setDate(2, new Date(taco.getCreateAt().getTime()));
            preparedStatement.setString(3,taco.getName());

            int affectedRows = preparedStatement.executeUpdate();
            if( affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet id = preparedStatement.getGeneratedKeys()) {
                if (id.next()) {
                    tacoId = id.getLong("id");
                    taco.setId(tacoId);
                    saveIngredientRefsDB(tacoId, taco.getIngredients());

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tacoId;
    }

    public void saveIngredientRefsDB (Long tacoId, List <Ingredients> ingredients) {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO taco_ingredients (taco_id, ingredients_id)" +
                    "values (?, ?) ");

            for (Ingredients ingredient: ingredients) {
                IngredientRef ingredientRef = new IngredientRef(tacoId, ingredient.getId());
                preparedStatement.setLong(1, ingredientRef.getTacoId());
                preparedStatement.setString(2, ingredientRef.getIngredientId());
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


