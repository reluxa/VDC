package org.reluxa.login;

import java.util.Iterator;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.reluxa.player.Player;
import org.reluxa.player.view.PlayerView;
import org.reluxa.player.view.RegisterPlayer;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.reluxa.vaadin.widget.GeneratedForm;
import org.reluxa.vaadin.widget.SimpleNavigationButton;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

//@CDIView(LoginView.LOGIN_VIEW)
public class LoginView extends VerticalLayout implements View, Button.ClickListener {

	public static final String LOGIN_VIEW = "";

	@Inject
	VaadinAccessControl accessControl;
	
	@Inject
	private CDIViewProvider viewProvider;
	
	private Player model;
	
	private GeneratedForm<Player> loginForm = null;

	public LoginView() {
		this.setStyleName("root");
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

	private void addEnterListenerOnPasswordField() {
	  Iterator<Component> iter = loginForm.getFormElements();
		while (iter.hasNext()) {
			Component comp = iter.next();
			if (comp instanceof PasswordField) {
				((PasswordField)comp).addShortcutListener(new ShortcutListener("Submit",ShortcutAction.KeyCode.ENTER, null) {
					@Override
					public void handleAction(Object sender, Object target) {
						buttonClick(null);
					}
				});
			}
		}
  }

	@Override
	public void enter(ViewChangeEvent event) {
		model = new Player();
		loginForm.setBean(model);
		addEnterListenerOnPasswordField();
		if (accessControl.isUserSignedIn()) {
			UI.getCurrent().getNavigator().navigateTo(PlayerView.VIEW_NAME);
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {
		if (!StringUtils.isEmpty(model.getEmail()) && !StringUtils.isEmpty(model.getPassword()) && accessControl.login(model)) {
			Notification.show("Login successful", Notification.Type.TRAY_NOTIFICATION);
			UI.getCurrent().getNavigator().navigateTo(PlayerView.VIEW_NAME);
		} else {
			Notification.show("Invalid username or password", Notification.Type.WARNING_MESSAGE);
		}
	}

}
