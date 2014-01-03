package org.reluxa.player.view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.reluxa.player.Player;
import org.reluxa.player.service.DuplicateUserException;
import org.reluxa.player.service.PlayerService;
import org.reluxa.vaadin.widget.GeneratedForm;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIView(RegisterPlayer.VIEW_NAME)
public class RegisterPlayer extends VerticalLayout implements View, Button.ClickListener {

	public static final String VIEW_NAME = "signup";
	
	@Inject
	PlayerService playerService;
	
	GeneratedForm<Player> form = new GeneratedForm<>(Player.class, RegisterPlayer.class);  
	
	Player user;
	
	@PostConstruct
	private void init() {
		VerticalLayout inner = new VerticalLayout();
		inner.setWidth(null);
		inner.addComponent(new Label("<h2>User Registration<h2>", ContentMode.HTML));
		setSizeFull();
		inner.addComponent(form);
		HorizontalLayout buttonLine = new HorizontalLayout();
		buttonLine.setSpacing(true);
		Button signupButton = new Button("Signup");
		signupButton.addClickListener(this);
		buttonLine.addComponent(signupButton);
		form.addComponent(buttonLine);
		addComponent(inner);
		setComponentAlignment(inner, Alignment.MIDDLE_CENTER);
  }
	
	@Override
	public void enter(ViewChangeEvent event) {
		user = new Player();
		form.setBean(user);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		try {
	    playerService.createUser(user);
	    UI.getCurrent().getNavigator().navigateTo("");
	    Notification.show("User created successfully.", Type.TRAY_NOTIFICATION);
    } catch (DuplicateUserException e) {
    	Notification.show("This email address already exists!", Type.ERROR_MESSAGE);
    }
		
	}
}
