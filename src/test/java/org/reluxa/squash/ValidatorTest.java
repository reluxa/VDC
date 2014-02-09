package org.reluxa.squash;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;
import org.reluxa.bid.Ticket;
import org.reluxa.player.Player;


public class ValidatorTest {

	@Test
	public void testPlayerValidator() {
		Player player = new Player();
		
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Player>> set =  validator.validate(player);
		for (ConstraintViolation<Player> cv : set) {
			System.out.println(cv.getMessage());
    }
	}
	
	@Test
	public void testTicketValidator() {
		Ticket ticket = new Ticket();
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Ticket>> set =  validator.validate(ticket);
		for (ConstraintViolation<Ticket> cv : set) {
			System.out.println(cv.getMessage());
    }
		assertTrue(set.size() > 0);
	}
	
	@Test
	public void testTicketValidator2() {
		Ticket ticket = new Ticket();
		ticket.setTicketCode("TG2167");
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Ticket>> set =  validator.validate(ticket);
		assertTrue(set.size() > 0);
	}
	
	@Test
	public void testTicketValidator_noError() {
		Ticket ticket = new Ticket();
		ticket.setTicketCode("TG2167");
		ticket.setPrice("3210");
		ticket.setCourt("Court");
		ticket.setDuration("59");
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Ticket>> set =  validator.validate(ticket);
		assertTrue(set.size() == 0);
	}
	
}
