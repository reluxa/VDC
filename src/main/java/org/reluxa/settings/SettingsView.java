package org.reluxa.settings;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.reluxa.AbstractView;
import org.reluxa.bid.service.BidEvaluator;
import org.reluxa.player.Player;
import org.reluxa.settings.service.SettingsServiceIF;
import org.reluxa.vaadin.widget.GeneratedForm;
import org.reluxa.vaadin.widget.Icon;
import org.reluxa.vaadin.widget.IconButtonFactory;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@CDIView(SettingsView.VIEW_NAME)
@RolesAllowed(value = Player.ROLE_ADMIN)
public class SettingsView extends AbstractView implements ClickListener {
	
	public static final String VIEW_NAME = "settings";

  private GeneratedForm<Config> form = new GeneratedForm<>(Config.class, SettingsView.class);

  private Config config;
  
  @Inject
  SettingsServiceIF settingsService;
  
  @Inject 
  BidEvaluator bidEvaluator;

	@Override
  public void enter(ViewChangeEvent event) {
		config = settingsService.getConfig();
		System.out.println(config);
		form.setBean(config);
  }

	@Override
  protected Component getContent() {
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		vl.setMargin(new MarginInfo(true, true, true, true));
		Label label = new Label("<h1>" + Icon.get("settings") + "Application settings</h1>", ContentMode.HTML);
		label.setWidth("100%");
		Button saveButton = IconButtonFactory.get("Save", "disk");
		saveButton.addClickListener(this);
		vl.addComponent(label);
		vl.addComponent(form);
		vl.addComponent(saveButton);
		
		
		Button runNow = IconButtonFactory.get("Run evaluation", "hammer2");
		runNow.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				bidEvaluator.runWeeklyEvaluation();				
			}
		});
		
		
		vl.addComponent(runNow);

		return vl;
  }

	@Override
  public void buttonClick(ClickEvent event) {
		settingsService.saveConfig(config);
  }

}
