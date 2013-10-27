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
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIView("signup")
public class SignupView extends VerticalLayout implements View,
		Button.ClickListener {

	@Inject
	LoginController loginController;

	private TextField username;

	private PasswordField password;
	
	private PasswordField retype;

	public SignupView() {
		setSizeFull();
		VerticalLayout inner = new VerticalLayout();
		inner.setWidth(null);
		inner.addComponent(new Label(
				"<h2>Please enter the desired username<h2>", ContentMode.HTML));
		FormLayout logingForm = new FormLayout();
		logingForm.setWidth(null);
		username = new TextField("username");
		logingForm.addComponent(username);
		password = new PasswordField("password");
		retype = new PasswordField("retype");
		logingForm.addComponent(password);
		logingForm.addComponent(retype);
		inner.addComponent(logingForm);
		HorizontalLayout buttonLine = new HorizontalLayout();
		buttonLine.setSpacing(true);
		Button signupButton = new Button("Signup");
		signupButton.addClickListener(this);
		buttonLine.addComponent(signupButton);
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
		loginController.createUser((String)username.getValue(),(String)username.getValue());
		UI.getCurrent().getNavigator().navigateTo("");
	}
}
