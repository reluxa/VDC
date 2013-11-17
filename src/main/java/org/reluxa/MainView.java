package org.reluxa;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.reluxa.model.Player;
import org.reluxa.vaadin.auth.VaadinAccessControl;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


@CDIView(MainView.VIEW_NAME)
@RolesAllowed(value=Player.ROLE_USER)
public class MainView extends VerticalLayout implements View {
	
	@Inject
	VaadinAccessControl accessControl;
	
	@Inject
	PlayerService playerService;
	
	BeanItemContainer<Player> container = new BeanItemContainer<>(Player.class);
	
	@PostConstruct
	public void init() {
		addComponent(new Label("Menu bar"));
		Button logout = new Button("Logout");
		logout.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				accessControl.logout();
				UI.getCurrent().getNavigator().navigateTo(LoginView.LOGIN_VIEW);
				Notification.show("Successfullly logged out.", Type.TRAY_NOTIFICATION);
			}
		});
		addComponent(logout);
		HorizontalLayout horizontal = new HorizontalLayout();
		Table table = new Table("Users", container);
		horizontal.addComponent(table);
		horizontal.addComponent(new Label("DetailView"));
		table.setSelectable(true);
		addComponent(horizontal);
	}
	

	public static final String VIEW_NAME = "main_view";
	
	@Override
	public void enter(ViewChangeEvent event) {
		container.addAll(playerService.getAllPlayers());

	}

}
