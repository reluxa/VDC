package org.reluxa.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

import org.reluxa.LoginView;
import org.reluxa.MainView;
import org.reluxa.player.RegisterPlayer;
import org.reluxa.vaadin.annotation.Detail;
import org.reluxa.vaadin.annotation.GUI;
import org.reluxa.vaadin.util.ValidationPattern;

import com.vaadin.ui.PasswordField;

@Data
public class Player {

	public static final String ROLE_USER = "user";
	
	@GUI({ 
		@Detail(context = RegisterPlayer.class, order = 1), 
		@Detail(context = LoginView.class, order = 1),
		@Detail(context = MainView.class, order = 1)
	})
	@Pattern(message = "Please provide a valid email address!", regexp = ValidationPattern.EMAIL_PATTERN)
	@NotNull
	private String email;

	@GUI({ 
		@Detail(context = RegisterPlayer.class, order = 2, type = PasswordField.class),
		@Detail(context = LoginView.class, order = 2, type = PasswordField.class)
	})
	@Size(min = 6, message = "Password minimum length is 6 characters")
	private String password;

	@GUI({
		@Detail(context = RegisterPlayer.class, order = 3, type = PasswordField.class) 
	})
	@Size(min = 6)
	private transient String passwordRetype;

	@GUI({ 
		@Detail(context = RegisterPlayer.class, order = 4),
		@Detail(context = MainView.class, order = 2)
	})
	@NotNull
	private String fullName;

	private List<Bid> bids;
	
	@GUI({ 
		@Detail(context = MainView.class, order = 3)
	})
	private List<String> roles;

	@GUI({ 
		@Detail(context = MainView.class, order = 3)
	})
	private Date membershipValidUntil;
}
