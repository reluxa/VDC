package org.reluxa;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.reluxa.model.Player;
import org.reluxa.player.RegisterPlayer;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.reluxa.vaadin.widget.GeneratedForm;
import org.reluxa.vaadin.widget.SimpleNavigationButton;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIView
public class LoginView extends VerticalLayout implements View, Button.ClickListener {

	public static final String LOGIN_VIEW = "";

	@Inject
	VaadinAccessControl accessControl;
	
	@Inject
	private CDIViewProvider viewProvider;
	
	private Player model;
	
	private GeneratedForm<Player> loginForm = null;

	public LoginView() {
		setSizeFull();
		VerticalLayout inner = new VerticalLayout();
		inner.setWidth(null);
		inner.addComponent(new Label("<h2>Please enter the login credentials<h2>", ContentMode.HTML));
		loginForm  = new GeneratedForm<Player>(Player.class, LoginView.class);
		inner.addComponent(loginForm);
		Button loginButton = new Button("Login");
		loginButton.addClickListener(this);
		HorizontalLayout buttonLine = new HorizontalLayout();
		buttonLine.setSpacing(true);
		buttonLine.addComponent(loginButton);
		Button signup = new SimpleNavigationButton("Signup", RegisterPlayer.VIEW_NAME);
		buttonLine.addComponent(signup);
		buttonLine.setComponentAlignment(loginButton, Alignment.TOP_RIGHT);
		loginForm.addComponent(buttonLine);
		addComponent(inner);
		setComponentAlignment(inner, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		System.out.println("Enter was called");
		model = new Player();
		loginForm.setBean(model);
		
		if (accessControl.isUserSignedIn()) {
			UI.getCurrent().getNavigator().navigateTo(MainView.VIEW_NAME);
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (!StringUtils.isEmpty(model.getEmail()) && !StringUtils.isEmpty(model.getPassword()) && accessControl.login(model)) {
			Notification.show("Login successful", Notification.Type.TRAY_NOTIFICATION);
			UI.getCurrent().getNavigator().navigateTo(MainView.VIEW_NAME);
		} else {
			Notification.show("Invalid username or password", Notification.Type.WARNING_MESSAGE);
		}
	}

}
