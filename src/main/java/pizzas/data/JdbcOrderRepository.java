package pizzas.data;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pizzas.IngredientRef;
import pizzas.Pizza;
import pizzas.PizzaOrder;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class JdbcOrderRepository implements OrderRepository{

    private JdbcOperations jdbcOperations;

    public JdbcOrderRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    @Transactional
    public PizzaOrder save(PizzaOrder order){
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Pizza_order" +
                                "(delivery_name, delivery_street, delivery_city," +
                                "delivery_state, delivery_zip, cc_number," +
                                "cc_expiration, cc_cvv, placed_at)" +
                                "values (?,?,?,?,?,?,?,?,?)",
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP
                );
        pscf.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());
        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                order.getDeliveryName(),
                                order.getDeliveryStreet(),
                                order.getDeliveryCity(),
                                order.getDeliveryState(),
                                order.getDeliveryZip(),
                                order.getCcNumber(),
                                order.getCcExpiration(),
                                order.getCcCVV(),
                                order.getPlacedAt()));

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, keyHolder);
        long orderId = keyHolder.getKey().longValue();
        order.setId(orderId);

        List<Pizza> pizzaList = order.getPizzaList();
        int i = 0;
        for(Pizza pizza : pizzaList){
            savePizza(orderId, i++, pizza);
        }
        return order;
    }

    private long savePizza(Long orderId, int orderKey, Pizza pizza){
        pizza.setCreationAt(new Date());
        PreparedStatementCreatorFactory pscf =
                new PreparedStatementCreatorFactory(
                        "insert into Pizza " +
                                "(name, created_at, pizza_order, pizza_order_key)" +
                                "values (?,?,?,?)",
                        Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
                );
        pscf.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
                pscf.newPreparedStatementCreator(
                        Arrays.asList(
                                pizza.getName(),
                                pizza.getCreationAt(),
                                orderId,
                                orderKey));

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(psc, generatedKeyHolder);
        long pizzaId = generatedKeyHolder.getKey().longValue();
        pizza.setId(pizzaId);

        saveIngredientRefs(pizzaId, pizza.getIngredients());

        return pizzaId;
    }


    private void saveIngredientRefs(long pizzaId, List<IngredientRef> ingredientRefs){
        int key = 0;
        for(IngredientRef ingredientRef: ingredientRefs){
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, pizza, pizza_key)" +
                            "values (?,?,?)",
                    ingredientRef.getIngredient(), pizzaId, key++);
        }
    }

}
