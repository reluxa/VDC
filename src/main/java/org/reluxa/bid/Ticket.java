package org.reluxa.bid;
import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

import org.reluxa.login.TicketValidation;
import org.reluxa.vaadin.annotation.Detail;
import org.reluxa.vaadin.annotation.GUI;
import org.reluxa.vaadin.util.ValidationPattern;

@Data
public class Ticket implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@GUI(detail = { 
			@Detail(context = TicketValidation.class, order = 1)
	})
	@Pattern(message = "Please provide a valid ticket code!", regexp = ValidationPattern.TICKET_PATTERN)
	@NotNull
	private String ticketCode;
	
	@GUI(detail = {
			@Detail(context = TicketValidation.class, order = 2, propertyName="Price (HUF)")
	})
	@Digits(integer=5,fraction=0)
	@NotNull
	private String price;
	
	@GUI(detail = {
			@Detail(context = TicketValidation.class, order = 3, propertyName="Duration (min)")
	})
	@Digits(integer=3,fraction=0)
	@NotNull
	private String duration;
	
	@GUI(detail = {
			@Detail(context = TicketValidation.class, order = 4, type=CourtCombobox.class)
	})
	@NotNull
	private String court;

}
