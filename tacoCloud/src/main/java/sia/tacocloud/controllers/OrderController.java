package sia.tacocloud.controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.common.Converter;
import sia.tacocloud.dao.IngredientsDao;
import sia.tacocloud.dao.OrderRepository;
import sia.tacocloud.data.dto.TacoOrderDTO;
import sia.tacocloud.data.model.IngredientByIdConverter;
import sia.tacocloud.data.model.TacosOrder;
import javax.validation.Valid;


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
    @PutMapping("/{orderId}")
    public TacosOrder putOrder(@PathVariable ("orderId") Long orderId, @RequestBody TacoOrderDTO orderDTO) {
        orderDTO.setId(orderId);
        TacosOrder order = Converter.convert(orderDTO, ingredientByIdConverter);
        return orderRepository.save(order);
    }

    @PatchMapping(path="/{orderId}", consumes="application/json")
    public  TacosOrder patchOrder(@PathVariable("orderId") Long orderId, @RequestBody TacoOrderDTO patchOrderDTO) {
        TacosOrder patchOrder = Converter.convert(patchOrderDTO, ingredientByIdConverter);
        TacosOrder order = orderRepository.findById(orderId).get();
        if(patchOrder.getDeliveryName() != null){
            order.setDeliveryName(patchOrder.getDeliveryName());
        }
        if(patchOrder.getDeliveryStreet() != null) {
            order.setDeliveryStreet(patchOrder.getDeliveryStreet());
        }
        if(patchOrder.getDeliveryCity() != null) {
            order.setDeliveryCity(patchOrder.getDeliveryCity());
        }
        if(patchOrder.getDeliveryState() != null) {
            order.setDeliveryState(patchOrder.getDeliveryState());
        }
        if(patchOrder.getDeliveryZip() != null) {
            order.setDeliveryZip(patchOrder.getDeliveryZip());
        }
        if(patchOrder.getCcNumber() != null) {
            order.setCcNumber(patchOrder.getCcNumber());
        }
        if(patchOrder.getCcExpiration() != null) {
            order.setCcExpiration(patchOrder.getCcExpiration());
        }
        if(patchOrder.getCcCVV() != null) {
            order.setCcCVV(patchOrder.getCcCVV());
        }
        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable("orderId") Long orderId) {
        try{
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException e){

        }
    }
}
