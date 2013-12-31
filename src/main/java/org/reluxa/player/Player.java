package org.reluxa.player;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

import org.reluxa.bid.Bid;
import org.reluxa.bid.CurrentWeekBidView;
import org.reluxa.login.LoginView;
import org.reluxa.model.IDObject;
import org.reluxa.model.SelfValidating;
import org.reluxa.model.Validatable;
import org.reluxa.player.view.PlayerView;
import org.reluxa.player.view.RegisterPlayer;
import org.reluxa.vaadin.annotation.Detail;
import org.reluxa.vaadin.annotation.GUI;
import org.reluxa.vaadin.annotation.Table;
import org.reluxa.vaadin.util.ValidationPattern;

import com.vaadin.ui.PasswordField;

@Data
@SelfValidating(message="passwords should match")
public class Player implements Validatable, IDObject {

	public static final String ROLE_USER = "user";
	public static final String ROLE_ADMIN = "admin";
	
	@GUI(
	detail = { 
		@Detail(context = RegisterPlayer.class, order = 1), 
		@Detail(context = LoginView.class, order = 1),
		@Detail(context = PlayerView.class, order = 1)
	}, table = {
		@Table(context = PlayerView.class, order = 2)
	})
	@Pattern(message = "Please provide a valid email address!", regexp = ValidationPattern.EMAIL_PATTERN)
	@NotNull
	private String email;

	@GUI(detail = { 
		@Detail(context = RegisterPlayer.class, order = 2, type = PasswordField.class),
		@Detail(context = LoginView.class, order = 2, type = PasswordField.class)
	})
	@Size(min = 6, message = "Password minimum length is 6 characters")
	private String password;

	@GUI(detail = {
		@Detail(context = RegisterPlayer.class, order = 3, type = PasswordField.class) 
	})
	@Size(min = 6)
	private transient String passwordRetype;

	@GUI(detail = { 
		@Detail(context = RegisterPlayer.class, order = 4),
		@Detail(context = PlayerView.class, order = 2)
	}, table = {
		@Table(context = PlayerView.class, order = 1),
		@Table(context = CurrentWeekBidView.class, order = 1)
	})
	@NotNull
	private String fullName;

	private List<Bid> bids;
	
	@GUI(detail = { 
		@Detail(context = PlayerView.class, order = 4)
	}, table = {
		@Table(context = PlayerView.class, order = 2)	
	})
	private boolean admin;

	@GUI(detail = { 
			@Detail(context = PlayerView.class, order = 3)
	}, table = {
			@Table(context = PlayerView.class, order = 4)	
		})
	private Date membershipValidUntil;

  public boolean isValid() {
	  return password != null && password.equals(passwordRetype);
  }
  
  private byte[] image;

  
  private transient long id;

}
