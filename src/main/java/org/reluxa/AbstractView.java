package org.reluxa;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.reluxa.bid.view.BidHistoryView;
import org.reluxa.bid.view.CurrentWeekBidView;
import org.reluxa.login.LoginView;
import org.reluxa.player.view.PlayerView;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.reluxa.vaadin.widget.Icon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.navigator.View;
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

public abstract class AbstractView extends VerticalLayout implements View {

  protected Logger log = LoggerFactory.getLogger(this.getClass()); 
  
  public enum EditMode {
    UPDATE, CREATE,
  }

  @Inject
  protected VaadinAccessControl accessControl;

  protected abstract Component getContent();

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

    Label label = new Label("Current user: " + accessControl.getPrincipalName());
    label.setWidth(null);
    label.setStyleName("v-menubar v-widget");
    menuLine.addComponent(label);
    menuLine.setComponentAlignment(label, Alignment.MIDDLE_RIGHT);

    page.addComponent(menuLine);
    page.addComponent(getContent());
    addComponent(page);
  }

  public MenuBar getMenuBar() {
    MenuBar menu = new MenuBar();
    menu.setHtmlContentAllowed(true);
    menu.addItem(Icon.get("calendar") + "Bids", new Command() {
      @Override
      public void menuSelected(MenuItem selectedItem) {
	UI.getCurrent().getNavigator().navigateTo(CurrentWeekBidView.VIEW_NAME);
      }
    });
    menu.addItem(Icon.get("ticket") + "Tickets", new Command() {
      @Override
      public void menuSelected(MenuItem selectedItem) {
	UI.getCurrent().getNavigator().navigateTo(CurrentWeekBidView.VIEW_NAME);
      }
    });
    menu.addItem(Icon.get("history") + "History", new Command() {
      @Override
      public void menuSelected(MenuItem selectedItem) {
	UI.getCurrent().getNavigator().navigateTo(BidHistoryView.VIEW_NAME);
      }
    });
    MenuItem admind = menu.addItem(Icon.get("wrench") + "Admin", null, null);
    admind.addItem(Icon.get("users") + "Players", new Command() {
      @Override
      public void menuSelected(MenuItem selectedItem) {
	UI.getCurrent().getNavigator().navigateTo(PlayerView.VIEW_NAME);
      }
    });

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

}
