package org.reluxa;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.reluxa.vaadin.auth.VaadinAccessControl;

import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractView extends VerticalLayout implements View {
	
	public enum EditMode {
		UPDATE,
		CREATE,
	}
	
	@Inject
	protected VaadinAccessControl accessControl;
	
	protected abstract Component getContent();
	
	@PostConstruct
	public void init() {
		setStyleName("root");
		addComponent(getMenuBar());
		addComponent(getContent());
	}
	
	public MenuBar getMenuBar() {
		MenuBar menu = new MenuBar();
		menu.addItem("Events", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				accessControl.logout();
				UI.getCurrent().getNavigator().navigateTo(LoginView.LOGIN_VIEW);
				Notification.show("Successfullly logged out.", Type.TRAY_NOTIFICATION);
			}
		});				
		menu.addItem("Players", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				accessControl.logout();
				UI.getCurrent().getNavigator().navigateTo(LoginView.LOGIN_VIEW);
				Notification.show("Successfullly logged out.", Type.TRAY_NOTIFICATION);
			}
		});		
		menu.addItem("Logout", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				accessControl.logout();
				UI.getCurrent().getNavigator().navigateTo(LoginView.LOGIN_VIEW);
				Notification.show("Successfullly logged out.", Type.TRAY_NOTIFICATION);
			}
		});
		menu.addItem("Profile", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				accessControl.logout();
				UI.getCurrent().getNavigator().navigateTo(LoginView.LOGIN_VIEW);
				Notification.show("Successfullly logged out.", Type.TRAY_NOTIFICATION);
			}
		});
		menu.setSizeFull();
		return menu;
	}

}
