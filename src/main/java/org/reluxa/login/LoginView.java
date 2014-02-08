package org.reluxa.login;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.reluxa.vaadin.auth.VaadinAccessControl;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@CDIView
public class LoginView extends VerticalLayout implements View {

	public static final String LOGIN_VIEW = "";

	@Inject
	private VaadinAccessControl accessControl;
	
	@Inject
	TicketValidation tv;
	
	@PostConstruct 
	public void init() {
		this.setStyleName("root");
		setSizeFull();
		HorizontalLayout horizontal = new HorizontalLayout();
		MyLoginForm loginForm = new MyLoginForm(accessControl);
		horizontal.addComponent(loginForm);
		Label label = new Label("");
		label.setWidth("100px");
		horizontal.addComponent(label);
		horizontal.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		horizontal.addComponent(tv);
		horizontal.setSpacing(true);
		horizontal.setMargin(true);
		//horizontal.setMargin(new MarginInfo(false, true, false, true));
		addComponent(horizontal);
		setComponentAlignment(horizontal, Alignment.MIDDLE_CENTER);
	}


	@Override
	public void enter(ViewChangeEvent event) {
		tv.reset();
	}

}
