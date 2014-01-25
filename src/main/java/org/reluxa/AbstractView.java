package org.reluxa;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.reluxa.bid.view.BidHistoryView;
import org.reluxa.bid.view.CurrentWeekBidView;
import org.reluxa.bid.view.TicketView;
import org.reluxa.login.LoginView;
import org.reluxa.message.MessageView;
import org.reluxa.player.Player;
import org.reluxa.player.view.PlayerView;
import org.reluxa.settings.SettingsView;
import org.reluxa.time.TimeServiceIF;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.reluxa.vaadin.widget.Icon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.wolfie.refresher.Refresher;
import com.github.wolfie.refresher.Refresher.RefreshListener;
import com.vaadin.navigator.View;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public abstract class AbstractView extends VerticalLayout implements View, RefreshListener {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	public enum EditMode {
		UPDATE, CREATE,
	}

	@Inject
	protected VaadinAccessControl accessControl;
	
	@Inject 
	protected TimeServiceIF timeService;

	protected abstract Component getContent();
	
	private DateTimeFormatter format = DateTimeFormat.forPattern("yyyy.MM.dd HH:mm");
	
	private Label label = null; 

	@PostConstruct
	public void init() {
		VerticalLayout page = new VerticalLayout();
		setStyleName("root");
		setSizeFull();

		HorizontalLayout menuLine = new HorizontalLayout();
		menuLine.setSizeFull();

		MenuBar menu = getMenuBar();
		menu.setWidth("100%");
		menuLine.addComponent(menu);
		menuLine.setExpandRatio(menu, 1f);
		menuLine.setComponentAlignment(menu, Alignment.MIDDLE_LEFT);

		label = new Label(Icon.get("user2") + accessControl.getPrincipalName()+" | "+getCurrentTimeStamp());
		label.setWidth(null);
		label.setContentMode(ContentMode.HTML);
		label.setStyleName("v-menubar v-widget");
		menuLine.addComponent(label);
		menuLine.setComponentAlignment(label, Alignment.MIDDLE_RIGHT);

		page.addComponent(menuLine);
		page.addComponent(getContent());
		addComponent(page);
		
		Refresher refresher = new Refresher();
		refresher.setRefreshInterval(60000);
		refresher.addListener(this);
		addExtension(refresher);
	}
	
	private String getCurrentTimeStamp() {
		LocalDateTime time = new LocalDateTime(timeService.getCurrentTime());
		return time.toString(format);
	}
	
	
	@Override
	public void refresh(Refresher source) {
		label.setValue(Icon.get("user2") + accessControl.getPrincipalName()+" | "+getCurrentTimeStamp());
	}

	public MenuBar getMenuBar() {
		MenuBar menu = new MenuBar();
		menu.setHtmlContentAllowed(true);
		addMenu(menu, "calendar", "Bids", CurrentWeekBidView.VIEW_NAME);
		addMenu(menu, "ticket", "Tickets", TicketView.VIEW_NAME);
		addMenu(menu, "history", "History", BidHistoryView.VIEW_NAME);
		addMenu(menu, "bullhorn", "Messenger", MessageView.VIEW_NAME);
		if (accessControl.isUserInRole(Player.ROLE_ADMIN)) {
			MenuItem admind = menu.addItem(Icon.get("wrench") + "Admin", null, null);
			addMenu(admind, "users", "Players", PlayerView.VIEW_NAME);
			addMenu(admind, "settings", "Settings", SettingsView.VIEW_NAME);
		}
		menu.addItem(Icon.get("logout") + "Logout", new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				accessControl.logout();
				UI.getCurrent().getNavigator().navigateTo(LoginView.LOGIN_VIEW);
				Notification.show("Successfullly logged out.", Type.TRAY_NOTIFICATION);
			}
		});
		return menu;
	}
	
	public MenuItem addMenu(MenuBar menu, String icon, String caption, final String viewName) {
		return menu.addItem(Icon.get(icon) + caption, new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(viewName);
			}
		});
	}
	
	public MenuItem addMenu(MenuItem item, String icon, String caption, final String viewName) {
		return item.addItem(Icon.get(icon) + caption, new Command() {
			@Override
			public void menuSelected(MenuItem selectedItem) {
				UI.getCurrent().getNavigator().navigateTo(viewName);
			}
		});
	}

}
