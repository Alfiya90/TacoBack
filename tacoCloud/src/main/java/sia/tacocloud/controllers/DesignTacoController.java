package sia.tacocloud.controllers;




import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import sia.tacocloud.dao.IngredientRepository;
import sia.tacocloud.dao.IngredientsDao;
import sia.tacocloud.dao.OrderRepository;
import sia.tacocloud.data.model.Ingredients;
import sia.tacocloud.data.model.Ingredients.Type;
import sia.tacocloud.data.model.TacosOrder;
import sia.tacocloud.data.model.Taco;

import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;
@AllArgsConstructor
@Slf4j // генерирует статическое свойство типа Logger
@RestController
@RequestMapping("/design")
/*@SessionAttributes("tacosOrder") // указывает на то что объект класса TacoOrder должен поддерживаться на уровне сеанса*/
public class DesignTacoController {
    private IngredientsDao ingredientsDao;
    IngredientRepository ingredientRepository;
    OrderRepository orderRepository;

    public DesignTacoController(IngredientsDao ingredientsDao) {
        this.ingredientsDao = ingredientsDao;
    }
    @Autowired
    public  DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @CrossOrigin
    @GetMapping("/ingredients")
    List<Ingredients> getIngredients() {
        return Arrays.asList(
                new Ingredients("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredients("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredients("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredients("CARN", "Carnitas", Type.PROTEIN),
                new Ingredients("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredients("LETC", "Lettuce", Type.VEGGIES),
                new Ingredients("CHED", "Cheddar", Type.CHEESE),
                new Ingredients("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredients("SLSA", "Salsa", Type.SAUCE),
                new Ingredients("SRCR", "Sour Cream", Type.SAUCE)
        );
    }
   /* @CrossOrigin
    @GetMapping("/show")
    List <Ingredients> show() {
        return ingredientsDao.getIngredients();
    }
*/

    //реализация через JDBCTemplate
    @CrossOrigin
    @GetMapping("/showingredients")
    public Iterable <Ingredients> ingredientsShow() {
        Iterable <Ingredients> ingr = ingredientRepository.findAll();
        return ingr;
    }

    @PostMapping("api/ingredients")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#{hasRole('ADMIN')}")
    public Ingredients saveIngredient (Ingredients ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @DeleteMapping("api/ingredients/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#{hasRole('ADMIN')}")
    public void deleteIngredient(@PathVariable("id") String ingredientId){
        ingredientRepository.deleteById(ingredientId);
    }




















    @ModelAttribute
    public void addIngredientsToModel (Model model){
        List<Ingredients> ingredients = Arrays.asList(
                new Ingredients("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredients("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredients("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredients("CARN", "Carnitas", Type.PROTEIN),
                new Ingredients("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredients("LETC", "Lettuce", Type.VEGGIES),
                new Ingredients("CHED", "Cheddar", Type.CHEESE),
                new Ingredients("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredients("SLSA", "Salsa", Type.SAUCE),
                new Ingredients("SRCR", "Sour Cream", Type.SAUCE)
        );

        model.addAttribute("ingredients", ingredients);

        Type[] types = Type.values();
        for (Type type: types) {
            log.info(filterByType(ingredients, type).toString());
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        }
    }

    @ModelAttribute(name = "tacosOrder")
    public TacosOrder order() {
        return new TacosOrder();
    }
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    private Iterable <Ingredients> filterByType(List <Ingredients> ingredients, Type type) {
        return ingredients.stream() // возвращает объект потока
                .filter(x -> x.getType().equals(type)) // отфильтровывает  по типу
                .collect(Collectors.toList()); //служит для преобразования элементов потока в результат  List
    }

   /* @CrossOrigin
    @PostMapping(consumes =  {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces =  {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public String processTaco(@Valid Taco taco, Errors errors, TacosOrder tacosOrder){
        if (errors.hasErrors()){
            return "Error";
        }
        tacosOrder.addTaco(taco);
        log.info("processing taco", taco);
        return "redirect:/orders/current";
    }*/






}
