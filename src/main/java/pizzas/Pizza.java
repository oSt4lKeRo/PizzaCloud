package pizzas;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Pizza {

	private long id;

	private Date creationAt = new Date();

	@NotNull
	@Size(min=5, message = "Name must be at least 5 characters long")
	private String name;

	@Size(min=1, message = "You must choose at least 1 ingredient")
	private List<IngredientRef> ingredients;
}
