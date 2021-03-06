package org.reluxa.settings;

import java.io.InputStream;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.reluxa.AbstractView;
import org.reluxa.bid.service.BidEvaluator;
import org.reluxa.db.ExportService;
import org.reluxa.player.Player;
import org.reluxa.settings.service.SettingsServiceIF;
import org.reluxa.vaadin.util.OnDemandFileDownloader;
import org.reluxa.vaadin.util.OnDemandFileDownloader.OnDemandStreamResource;
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
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
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
  
  @Inject
  ExportService exportService;

	@Override
  public void enter(ViewChangeEvent event) {
		config = settingsService.getConfig();
		System.out.println(config);
		form.setBean(config);
  }
	
	private class ResetClickListener implements ClickListener {
		@Override
    public void buttonClick(ClickEvent event) {
			settingsService.resetConfig();
			enter(null);
			Notification.show("Settings have been restored!", Type.TRAY_NOTIFICATION);
    }
	}
	
	private class BidEvaluationClickListener implements ClickListener {
		@Override
    public void buttonClick(ClickEvent event) {
			bidEvaluator.runWeeklyEvaluation();
			Notification.show("Weekly evaluation executed!", Type.TRAY_NOTIFICATION);
    }
	}
	
	private String getCurrentTimeStamp() {
		LocalDateTime dt = new LocalDateTime();
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd_HHmm");
		return dt.toString(format);
	}
	

	@Override
  protected Component getContent() {
		Panel panel = new Panel();
		panel.setHeight("100%");
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		vl.setMargin(new MarginInfo(true, true, true, true));
		Label label = new Label("<h1>" + Icon.get("settings") + "Application settings</h1>", ContentMode.HTML);
		label.setWidth("100%");
		Button saveButton = IconButtonFactory.get("Save", "disk");
		saveButton.addClickListener(this);
		
		Button resetButton = IconButtonFactory.get("Reset", "undo");
		resetButton.addClickListener(new ResetClickListener());

		HorizontalLayout buttonLine = new HorizontalLayout();
		buttonLine.setSpacing(true);
		buttonLine.addComponent(saveButton);
		buttonLine.addComponent(resetButton);


		vl.addComponent(label);
		vl.addComponent(form);
		vl.addComponent(buttonLine);
		
		Button runNow = IconButtonFactory.get("Run evaluation", "hammer2");
		runNow.addClickListener(new BidEvaluationClickListener());
		vl.addComponent(runNow);
		

		Button export = createExportButton();
		vl.addComponent(export);
		
		panel.setContent(vl);
		return panel;
  }


	private Button createExportButton() {
		Button export = IconButtonFactory.get("Export", "download2");
		OnDemandFileDownloader ond = new OnDemandFileDownloader(new OnDemandStreamResource() {
			@Override
			public InputStream getStream() {
				return IOUtils.toInputStream(exportService.getExportXML());
			}
			
			@Override
			public String getFilename() {
				return "export_"+getCurrentTimeStamp()+".xml";
			}
		});
		ond.extend(export);
		return export;
	}

	@Override
  public void buttonClick(ClickEvent event) {
		settingsService.saveConfig(config);
		Notification.show("Settings have been saved!", Type.TRAY_NOTIFICATION);
  }

}
