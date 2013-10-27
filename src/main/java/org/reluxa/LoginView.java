package org.reluxa;

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

public class LoginView extends VerticalLayout implements View, Button.ClickListener {
	
	

	public LoginView() {
		setSizeFull();
		VerticalLayout inner = new VerticalLayout();
		inner.setWidth(null);
		inner.addComponent(new Label("<h2>Please enter the login credentials<h2>",ContentMode.HTML));
		FormLayout logingForm = new FormLayout();
		logingForm.setWidth(null);
		logingForm.addComponent(new TextField("username"));
		logingForm.addComponent(new PasswordField("password"));
		inner.addComponent(logingForm);
		Button loginButton = new Button("Zita");
		loginButton.addClickListener(this);
		HorizontalLayout buttonLine = new HorizontalLayout();
		buttonLine.setSpacing(true);
		buttonLine.addComponent(loginButton);
		buttonLine.addComponent(new Button("Signup"));
		buttonLine.setComponentAlignment(loginButton, Alignment.TOP_RIGHT);
		logingForm.addComponent(buttonLine);
		addComponent(inner);
		setComponentAlignment(inner, Alignment.MIDDLE_CENTER);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
//        Notification.show("Welcome to the Animal Farm2");		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		UI.getCurrent().getNavigator().removeView("");
		UI.getCurrent().getNavigator().addView("other", new OtherView());
		UI.getCurrent().getNavigator().navigateTo("other");
	}

}
