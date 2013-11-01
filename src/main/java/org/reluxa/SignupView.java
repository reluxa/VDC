package org.reluxa;

import javax.inject.Inject;

import org.reluxa.model.User;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIView("signup")
public class SignupView extends VerticalLayout implements View,
		Button.ClickListener {

	@Inject
	LoginController loginController;
	
	private User user = new User();

	public SignupView() {
		VerticalLayout inner = new VerticalLayout();
		inner.setWidth(null);
		inner.addComponent(new Label(
				"<h2>Please enter the desired username<h2>", ContentMode.HTML));

		setSizeFull();
		User user = new User();
		Item item = new BeanItem<User>(user);
		FormLayout form = new FormLayout();
	    FieldGroup fieldGroup = new FieldGroup(item);
	    fieldGroup.setBuffered(false);
	    
	    fieldGroup.getUnboundPropertyIds();
        for (final Object propertyId : fieldGroup.getUnboundPropertyIds()) {
            form.addComponent(fieldGroup.buildAndBind(propertyId));
        }
		
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
		// Notification.show("Welcome to the Animal Farm2");
	}

	@Override
	public void buttonClick(ClickEvent event) {
		loginController.createUser(user);
		UI.getCurrent().getNavigator().navigateTo("");
	}
}
