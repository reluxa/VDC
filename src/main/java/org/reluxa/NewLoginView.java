package org.reluxa;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.reluxa.player.RegisterPlayer;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.reluxa.vaadin.widget.SimpleNavigationButton;

import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIView
public class NewLoginView extends VerticalLayout implements View {

	public static final String LOGIN_VIEW = "";

	@Inject
	VaadinAccessControl accessControl;

	@Inject
	private CDIViewProvider viewProvider;

	public NewLoginView() {
		this.setStyleName("root");
		setSizeFull();
		VerticalLayout inner = new VerticalLayout();

		FormLayout formLayout = new FormLayout();

		LoginForm loginForm = new LoginForm() {
			@Override
			protected void login() {
				String username = getUserNameField().getValue();
				String password = getPasswordField().getValue();
				if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password) && accessControl.login(username, password)) {
					Notification.show("Login was successful!", Notification.Type.TRAY_NOTIFICATION);
					UI.getCurrent().getNavigator().navigateTo(PlayerView.VIEW_NAME);
				} else {
					Notification.show("Invalid username or password!", Notification.Type.WARNING_MESSAGE);
				}
			}
		};

		inner.setWidth(null);
		inner.addComponent(new Label("<h2>Please enter the login credentials<h2>", ContentMode.HTML));

		formLayout.addComponent(loginForm.getUserNameField());
		formLayout.addComponent(loginForm.getPasswordField());

		addEnterListenerOnPasswordField(loginForm.getPasswordField(), loginForm.getLoginButton());

		inner.addComponent(formLayout);

		HorizontalLayout buttonLine = new HorizontalLayout(loginForm.getLoginButton(), new SimpleNavigationButton("Signup",
		    RegisterPlayer.VIEW_NAME));
		buttonLine.setSpacing(true);
		inner.addComponent(buttonLine);
		addComponent(inner);
		setComponentAlignment(inner, Alignment.MIDDLE_CENTER);
	}

	/**
	 * Submits the form by pressing enter.
	 * 
	 * @param field
	 * @param loginButton
	 */
	private void addEnterListenerOnPasswordField(PasswordField field, final Button loginButton) {
		field.addShortcutListener(new ShortcutListener("Submit", ShortcutAction.KeyCode.ENTER, null) {
			@Override
			public void handleAction(Object sender, Object target) {
				loginButton.click();
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

}
