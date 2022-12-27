package sia.tacocloud.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sia.tacocloud.data.model.IngredientRef;
import sia.tacocloud.data.model.Ingredients;
import sia.tacocloud.data.model.Taco;
import sia.tacocloud.data.model.TacosOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JDBCOrderRepository /*implements OrderRepository*/ {
    private JdbcOperations jdbcOperations;
    public JDBCOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Transactional
    public TacosOrder save(TacosOrder order) {
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(
                "INSERT INTO tacos_order" + "(delivery_name, delivery_street, delivery_city," +
                        " delivery_state, delivery_zip, cc_number, cc_expiration, cccvv, placed_at) " + "values (?,?,?,?,?,?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR , Types.VARCHAR , Types.VARCHAR , Types.VARCHAR,Types.VARCHAR, Types.VARCHAR , Types.VARCHAR, Types.TIMESTAMP);
        pscf.setReturnGeneratedKeys(true);
        order.setPlacedAt(new Date());
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator (Arrays.asList(
                order.getDeliveryName(),
                order.getDeliveryStreet(),
                order.getDeliveryCity(),
                order.getDeliveryState(),
                order.getDeliveryZip(),
                order.getCcNumber(),
                order.getCcExpiration(),
                order.getCcCVV(),
                order.getPlacedAt()
                
        ));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco: tacos) {
            saveTaco(orderId, taco);
        }
        return order;
    }

    private long saveTaco(Long orderId, Taco taco) {
        taco.setCreateAt(new Date());
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("INSERT INTO taco" +
                "(id, create_at, name)" + "values (?, ?, ?)",
                Types.BIGINT, Types.TIMESTAMP, Types.VARCHAR
                );
        pscf.setReturnGeneratedKeys(true);
        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(
                orderId,
                taco.getCreateAt(),
                taco.getName()
        ));
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long tacoId = keyHolder.getKey().longValue();
        taco.setId(tacoId);

        saveIngredientRefs(tacoId, taco.getIngredients());
        return tacoId;
    }

    private  void saveIngredientRefs(Long tacoId, List <Ingredients> ingredients) {
        int key = 0;
        for (Ingredients ingredient: ingredients) {
            IngredientRef ingredientRef = new IngredientRef(tacoId, ingredient.getId());
            jdbcOperations.update("INSERT INTO taco_ingredients(taco_id, ingredients_id)" + "values(?,?)",
                    ingredientRef.getTacoId(), ingredientRef.getIngredientsId());

        }
    }

}
