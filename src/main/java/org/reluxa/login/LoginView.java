package org.reluxa.login;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.reluxa.vaadin.auth.VaadinAccessControl;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;

@CDIView
public class LoginView extends VerticalLayout implements View {

	public static final String LOGIN_VIEW = "";

	@Inject
	VaadinAccessControl accessControl;
	
	@PostConstruct 
	public void init() {
		this.setStyleName("root");
		setSizeFull();
		MyLoginForm loginForm = new MyLoginForm(accessControl);
		addComponent(loginForm);
		setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);
	}


	@Override
	public void enter(ViewChangeEvent event) {
	}

}
