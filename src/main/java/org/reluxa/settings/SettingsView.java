package org.reluxa.settings;

import javax.annotation.security.RolesAllowed;

import org.reluxa.AbstractView;
import org.reluxa.player.Player;
import org.reluxa.vaadin.widget.GeneratedForm;
import org.reluxa.vaadin.widget.Icon;
import org.reluxa.vaadin.widget.IconButtonFactory;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@CDIView(SettingsView.VIEW_NAME)
@RolesAllowed(value = Player.ROLE_USER)
public class SettingsView extends AbstractView {
	
	public static final String VIEW_NAME = "settings";

  private GeneratedForm<Config> form = new GeneratedForm<>(Config.class, SettingsView.class);
	
	@Override
  public void enter(ViewChangeEvent event) {
  }

	@Override
  protected Component getContent() {
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		vl.setMargin(new MarginInfo(true, true, true, true));
		
		Label label = new Label("<h1>" + Icon.get("settings") + "Application settings</h1>", ContentMode.HTML);
		label.setWidth("100%");
		form.setBean(new Config());
		
		Button saveButton = IconButtonFactory.get("Save", "disk");

		vl.addComponent(label);
		vl.addComponent(form);
		vl.addComponent(saveButton);
		return vl;
  }

}
