import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;
import org.reluxa.model.Player;



public class PlayerValidatorTest {

	@Test
	public void testPlayerValidator() {
		Player player = new Player();
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Player>> set =  validator.validate(player);
		for (ConstraintViolation<Player> cv : set) {
			System.out.println(cv.getMessage());
    }
	}
	
}
