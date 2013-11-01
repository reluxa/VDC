package org.reluxa;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIView("login")
public class LoginView extends VerticalLayout implements View,
		Button.ClickListener {

	@Inject
	private LoginController loginController;

	private TextField username;

	private PasswordField password;

	public LoginView() {
		setSizeFull();
		VerticalLayout inner = new VerticalLayout();
		inner.setWidth(null);
		inner.addComponent(new Label(
				"<h2>Please enter the login credentials<h2>", ContentMode.HTML));
		FormLayout logingForm = new FormLayout();
		logingForm.setWidth(null);
		username = new TextField("username");
		username.setRequired(true);
		logingForm.addComponent(username);
		password = new PasswordField("password");
		password.setRequired(true);
		logingForm.addComponent(password);
		inner.addComponent(logingForm);
		Button loginButton = new Button("Login");
		loginButton.addClickListener(this);
		HorizontalLayout buttonLine = new HorizontalLayout();
		buttonLine.setSpacing(true);
		buttonLine.addComponent(loginButton);
		Button signup = new Button("Signup");
		signup.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo("signup");
			}
		});
		buttonLine.addComponent(signup);
		buttonLine.setComponentAlignment(loginButton, Alignment.TOP_RIGHT);
		logingForm.addComponent(buttonLine);
		addComponent(inner);
		setComponentAlignment(inner, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// Notification.show("Welcome to the Animal Farm2");
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (loginController.queryUser((String) username.getValue(),
				(String) username.getValue())) {
			Notification.show("Login successful",
					Notification.Type.WARNING_MESSAGE);
		} else {
			Notification.show("Invalid username or password",
					Notification.Type.WARNING_MESSAGE);
		}
	}

}
