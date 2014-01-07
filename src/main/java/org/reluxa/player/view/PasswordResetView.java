package org.reluxa.player.view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.reluxa.login.service.LoginService;
import org.reluxa.player.Player;
import org.reluxa.vaadin.widget.GeneratedForm;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@CDIView(PasswordResetView.VIEW_NAME)
public class PasswordResetView extends VerticalLayout implements View, Button.ClickListener {

  public static final String VIEW_NAME = "passwordreset";
  
  @Inject
  LoginService loginService;;

  GeneratedForm<Player> form = new GeneratedForm<>(Player.class, PasswordResetView.class);

  Player user;
  
  String uuid;

  @PostConstruct
  private void init() {
    setStyleName("root");
    VerticalLayout inner = new VerticalLayout();
    inner.setWidth(null);
    inner.addComponent(new Label("<h2>Password reset<h2>", ContentMode.HTML));
    setSizeFull();
    inner.addComponent(form);
    HorizontalLayout buttonLine = new HorizontalLayout();
    buttonLine.setSpacing(true);
    Button signupButton = new Button("Reset");
    signupButton.addClickListener(this);
    buttonLine.addComponent(signupButton);
    form.addComponent(buttonLine);
    addComponent(inner);
    setComponentAlignment(inner, Alignment.MIDDLE_CENTER);
  }

  @Override
  public void enter(ViewChangeEvent event) {
    uuid = Page.getCurrent().getLocation().getQuery().split("=")[1];
    user = new Player();
    form.setBean(user);
  }

  @Override
  public void buttonClick(ClickEvent event) {
    if (!StringUtils.isEmpty(uuid) && form.isValid() && StringUtils.equals(user.getPassword(), user.getPasswordRetype())) {
      boolean result = loginService.resetPassword(uuid, user.getPassword());
      if (result) {
	Notification.show("Password was changed successfully!", Type.HUMANIZED_MESSAGE);
	UI.getCurrent().getPage().setLocation(VaadinService.getCurrentRequest().getContextPath());
      } else {
	Notification.show("Unable to update password!", Type.ERROR_MESSAGE);
      }
    } else {
      Notification.show("Input is not valid.", Type.ERROR_MESSAGE);
    }
  }
}
