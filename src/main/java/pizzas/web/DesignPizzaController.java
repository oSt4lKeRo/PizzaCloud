package pizzas.web;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import pizzas.Ingredient.Type;
import pizzas.Ingredient;
import pizzas.Pizza;
import pizzas.PizzaOrder;
import pizzas.data.IngredientRepository;


@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("pizzaOrder")
public class DesignPizzaController {

	private IngredientRepository ingredientRepo;

	@Autowired
	public DesignPizzaController(IngredientRepository ingredientRepo){
		this.ingredientRepo = ingredientRepo;
	}

//	@ModelAttribute
//	public void addIngredientsModel(Model model) {
//		List<Ingredient> ingredients = Arrays.asList(
//				new Ingredient("CHDO", "Cheese Dough", Ingredient.Type.WRAP),
//				new Ingredient("CLDO", "Classic Dough", Ingredient.Type.WRAP),
//				new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
//				new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
//				new Ingredient("TMTO", "Tomatoes", Ingredient.Type.VEGGIES),
//				new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
//				new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
//				new Ingredient("MZRL", "Mozzarella", Ingredient.Type.CHEESE),
//				new Ingredient("PSTO", "Pesto", Ingredient.Type.SAUCE),
//				new Ingredient("TMSA", "Tomato Sauce", Ingredient.Type.SAUCE)
//		);
//
//		Ingredient.Type[] types = Ingredient.Type.values();
//		for(Type type : types){
//			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
//		}
//	}

	@ModelAttribute
	public void addIngredientToModel(Model model){
		Iterable<Ingredient> ingredients = ingredientRepo.findAll();
		Type[] types = Ingredient.Type.values();
		for(Type type : types){
			model.addAttribute(type.toString().toLowerCase(),
			filterByType(ingredients, type));
		}
	}

	@ModelAttribute(name = "pizzaOrder")
	public PizzaOrder order(){
		return new PizzaOrder();
	}

	@ModelAttribute(name = "pizza")
	public Pizza pizza(){
		return new Pizza();
	}

	@GetMapping
	public String showDesignForm(){
		return "design";
	}

	@PostMapping
	public String processPizza(@Valid Pizza pizza, Errors errors,
							   @ModelAttribute PizzaOrder pizzaOrder){
		if(errors.hasErrors()){
			return "design";
		}

		pizzaOrder.addPizza(pizza);
		log.info("Processing pizza: {}", pizza);

		return "redirect:/orders/current";
	}

	private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type){
		return StreamSupport.stream(ingredients.spliterator(), false)
				.filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}

}
