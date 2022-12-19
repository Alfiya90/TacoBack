package sia.tacocloud.controllers;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.common.Converter;
import sia.tacocloud.dao.IngredientsDao;
import sia.tacocloud.dao.OrderRepository;
import sia.tacocloud.data.dto.TacoOrderDTO;
import sia.tacocloud.data.model.IngredientByIdConverter;
import sia.tacocloud.data.model.TacosOrder;


@AllArgsConstructor
@Slf4j
@RequestMapping("/orders")
@RestController
@SessionAttributes("tacoOrder")
public class OrderController {
    private final IngredientByIdConverter ingredientByIdConverter;
    private final OrderRepository orderRepository;
    private final IngredientsDao ingredientsDao;



    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

  /*  @PostMapping
    public String  processOrder(@Valid TacosOrder order, Errors errors, SessionStatus sessionStatus){
        if (errors.hasErrors()){
            return "error";
        }
        log.info("Order submitted: {}", order);
        sessionStatus.setComplete();
        return *//*"redirect:/"*//* order.toString();
    }*/


/*    @CrossOrigin
    @PostMapping(consumes =  {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces =  {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TacosOrder  order(@Valid @RequestBody TacoOrderDTO orderDTO){
        log.info("Order submitted: {}", orderDTO);
        TacosOrder tacosOrder = Converter.convert(orderDTO, ingredientByIdConverter);
        return tacosOrder;
    }*/

    @CrossOrigin
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public TacosOrder order(@Valid @RequestBody TacoOrderDTO orderDTO) {
        TacosOrder order = Converter.convert(orderDTO, ingredientByIdConverter);

        //сохранение через JDBCTemplate
        orderRepository.save(order);

        // coxранение через prepareStatement
        //ingredientsDao.saveOrder(order);


        return order;
    }
}
