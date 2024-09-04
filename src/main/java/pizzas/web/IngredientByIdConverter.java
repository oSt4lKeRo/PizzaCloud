package pizzas.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import pizzas.Ingredient;
import pizzas.Ingredient.Type;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

	private Map<String, Ingredient> ingredientMap = new HashMap<>();

	public IngredientByIdConverter(){
		ingredientMap.put("CHDO", new Ingredient("CHDO", "Cheese Dough", Ingredient.Type.WRAP));
		ingredientMap.put("CLDO", new Ingredient("CLDO", "Classic Dough", Ingredient.Type.WRAP));
		ingredientMap.put("GRBF", new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
		ingredientMap.put("CARN", new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
		ingredientMap.put("TMTO", new Ingredient("TMTO", "Tomatoes", Ingredient.Type.VEGGIES));
		ingredientMap.put("LETC", new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
		ingredientMap.put("CHED", new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
		ingredientMap.put("MZRL", new Ingredient("MZRL", "Mozzarella", Ingredient.Type.CHEESE));
		ingredientMap.put("PSTO", new Ingredient("PSTO", "Pesto", Ingredient.Type.SAUCE));
		ingredientMap.put("TMSA", new Ingredient("TMSA", "Tomato Sauce", Ingredient.Type.SAUCE));
	}

	@Override
	public Ingredient convert(String id){
		return ingredientMap.get(id);
	}
}
