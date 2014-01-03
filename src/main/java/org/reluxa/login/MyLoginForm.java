package org.reluxa.login;

import org.apache.commons.lang3.StringUtils;
import org.reluxa.player.view.PlayerView;
import org.reluxa.player.view.RegisterPlayer;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.reluxa.vaadin.widget.SimpleNavigationButton;

import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MyLoginForm extends LoginForm {

	VaadinAccessControl accessControl;

	volatile boolean passwordSaved = false;
	
	public MyLoginForm(VaadinAccessControl accessControl) {
		this.accessControl = accessControl; 
		VerticalLayout inner = new VerticalLayout();
		FormLayout formLayout = new FormLayout();
		inner.setWidth(null);
		inner.addComponent(new Label("<h2>Please enter the login credentials<h2>", ContentMode.HTML));
		formLayout.addComponent(getUserNameField());
		formLayout.addComponent(getPasswordField());
		HorizontalLayout buttonLine = new HorizontalLayout(getLoginButton(), 
									  new SimpleNavigationButton("Signup",RegisterPlayer.VIEW_NAME));
		buttonLine.setSpacing(true);
		inner.addComponent(formLayout);
		inner.addComponent(buttonLine);
		setContent(inner);
	}

	@Override
  protected void login() {
		String username = getUserNameField().getValue();
		String password = getPasswordField().getValue();
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password) && accessControl.login(username, password)) {
			Notification.show("Login was successful!", Notification.Type.TRAY_NOTIFICATION);
		} else {
			Notification.show("Invalid username or password!", Notification.Type.WARNING_MESSAGE);
		}
	}
	
	@Override
	protected void submitCompleted() {
		super.submitCompleted();
		UI.getCurrent().getNavigator().navigateTo(PlayerView.VIEW_NAME);
	}


}
