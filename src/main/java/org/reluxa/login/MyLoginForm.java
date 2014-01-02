package org.reluxa.login;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.reluxa.player.view.PlayerView;
import org.reluxa.player.view.RegisterPlayer;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.reluxa.vaadin.widget.SimpleNavigationButton;

import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.navigator.Navigator;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MyLoginForm extends LoginForm {

	@Inject
	VaadinAccessControl accessControl;

	AtomicInteger conditions = new AtomicInteger(0);
	
	Navigator navigator;
	
	volatile boolean passwordSaved = false;
	
	public MyLoginForm() {
		navigator = UI.getCurrent().getNavigator();
		
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
//			final Refresher refresher = new Refresher();
//			refresher.addListener(new RefreshListener() {
//				@Override
//				public void refresh(Refresher source) {
//					System.out.println("Refreshing....");
//					if (passwordSaved) {
//						((ExampleApp)UI.getCurrent()).removeExtension(refresher);
//						System.out.println("Refresh called");
//						UI.getCurrent().getNavigator().navigateTo(PlayerView.VIEW_NAME);
//					}
//				}
//			});
//			((ExampleApp)UI.getCurrent()).addExtension(refresher);
			
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
