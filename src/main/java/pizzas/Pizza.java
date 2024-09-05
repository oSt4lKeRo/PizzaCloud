package pizzas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;
import java.util.List;

@Data
@Table
@EqualsAndHashCode(exclude = "createdAt")
public class Pizza {

	@Id
	private Long id;

	private Date creationAt = new Date();

	@NotNull
	@Size(min=5, message = "Name must be at least 5 characters long")
	private String name;

	@Size(min=1, message = "You must choose at least 1 ingredient")
	private List<IngredientRef> ingredients;

	public void addIngredient(Ingredient pizza) {
		this.ingredients.add(new IngredientRef(pizza.getId()));
	}
}
