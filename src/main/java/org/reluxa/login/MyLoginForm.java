package org.reluxa.login;

import org.apache.commons.lang3.StringUtils;
import org.reluxa.bid.view.CurrentWeekBidView;
import org.reluxa.player.view.RegisterPlayer;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.reluxa.vaadin.widget.Icon;
import org.reluxa.vaadin.widget.IconButtonFactory;
import org.reluxa.vaadin.widget.SimpleNavigationButton;

import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MyLoginForm extends LoginForm {

	private VaadinAccessControl accessControl;

	volatile boolean passwordSaved = false;

	private Button resetPasswordButton = null;

	private volatile boolean authOk;

	public MyLoginForm(VaadinAccessControl accessControl) {
		this.accessControl = accessControl;
		VerticalLayout inner = new VerticalLayout();
		FormLayout formLayout = new FormLayout();
		inner.setWidth(null);
		Label loginlab = new Label("<h2>Login credentials<h2>", ContentMode.HTML);
		inner.addComponent(loginlab);
		formLayout.addComponent(getUserNameField());
		formLayout.addComponent(getPasswordField());
		Button loginButton = getLoginButton();
		loginButton.setHtmlContentAllowed(true);
		loginButton.setCaption(Icon.get("enter") + "Login");
		HorizontalLayout buttonLine = new HorizontalLayout(loginButton, new SimpleNavigationButton(Icon.get("user-add")
		    + "Signup", RegisterPlayer.VIEW_NAME));
		buttonLine.setSpacing(true);

		resetPasswordButton = IconButtonFactory.get("Reset password", "mail2");
		resetPasswordButton.setVisible(false);
		resetPasswordButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				boolean result = MyLoginForm.this.accessControl.getLoginService().passwordResetRequest(
				    getUserNameField().getValue());
				if (result) {
					Notification.show("Please check your email", Type.HUMANIZED_MESSAGE);
				} else {
					Notification.show("Unable to send password reset email", Type.ERROR_MESSAGE);
				}
			}
		});

		buttonLine.addComponent(resetPasswordButton);

		inner.addComponent(formLayout);
		inner.addComponent(buttonLine);
		setContent(inner);
	}

	@Override
	protected void login() {
		authOk = false;
		String username = getUserNameField().getValue();
		String password = getPasswordField().getValue();
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password) && accessControl.login(username, password)) {
			Notification.show("Login was successful!", Notification.Type.TRAY_NOTIFICATION);
			authOk = true;
		} else {
			Notification.show("Invalid username or password!", Notification.Type.WARNING_MESSAGE);
			resetPasswordButton.setVisible(true);
		}
	}

	@Override
	protected void submitCompleted() {
		if (authOk) {
			UI.getCurrent().getNavigator().navigateTo(CurrentWeekBidView.VIEW_NAME);
		}
	}

}
