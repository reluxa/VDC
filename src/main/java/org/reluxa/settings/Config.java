package org.reluxa.settings;
import java.util.Date;

import lombok.Data;

import org.reluxa.vaadin.annotation.Detail;
import org.reluxa.vaadin.annotation.GUI;
import org.reluxa.vaadin.widget.TimeField;

import com.vaadin.ui.RichTextArea;

@Data
public class Config {

	@GUI(detail = { 
		@Detail(context = SettingsView.class, order = 1), 
	})
	private Integer numberOfEvents;

	@GUI(detail = { 
			@Detail(context = SettingsView.class, order = 2), 
	})
	private boolean fixedTime;

	@GUI(detail = { 
			@Detail(context = SettingsView.class, order = 3, type=TimeField.class), 
	})
	private Date currentTime;
	
	
	@GUI(detail = { 
			@Detail(context = SettingsView.class, order = 4, type=RichTextArea.class), 
	})
	private String passwordResetTemplate;
	
}
