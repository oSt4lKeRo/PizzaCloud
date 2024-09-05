package pizzas;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pizzas.data.IngredientRepository;
import pizzas.Ingredient.Type;

@SpringBootApplication
public class PizzaCloudApplication {
	public static void main(String[] args) {
		SpringApplication.run(PizzaCloudApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo) {
		return args -> {
			repo.deleteAll(); // TODO: Quick hack to avoid tests from stepping on each other with constraint violations
			repo.save(new Ingredient("CHDO", "Cheese Dough", Type.WRAP));
			repo.save(new Ingredient("CLDO", "Classic Dough", Type.WRAP));
			repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
			repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
			repo.save(new Ingredient("TMTO", "Tomatoes", Type.VEGGIES));
			repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
			repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
			repo.save(new Ingredient("MZRL", "Mozzarella", Type.CHEESE));
			repo.save(new Ingredient("PSTO", "Pesto", Type.SAUCE));
			repo.save(new Ingredient("TMSA", "Tomato Sauce", Type.SAUCE));
		};
	}
}