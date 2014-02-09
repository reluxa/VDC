package org.reluxa.settings;
import java.util.Date;

import lombok.Data;

import org.reluxa.model.IDObject;
import org.reluxa.vaadin.annotation.Detail;
import org.reluxa.vaadin.annotation.GUI;
import org.reluxa.vaadin.widget.TimeField;

import com.vaadin.ui.RichTextArea;

@Data
public class Config implements IDObject {

	@GUI(detail = { 
		@Detail(context = SettingsView.class, order = 1), 
	})
	private Integer numberOfEventsPerWeek;

	@GUI(detail = { 
			@Detail(context = SettingsView.class, order = 2), 
	})
	private boolean fixedTime;

	@GUI(detail = { 
			@Detail(context = SettingsView.class, order = 3, type=TimeField.class), 
	})
	private Date currentTime;

	@GUI(detail = { 
			@Detail(context = SettingsView.class, order = 4), 
	})
	private String senderEmailAddress;
	
	@GUI(detail = { 
			@Detail(context = SettingsView.class, order = 5), 
	})
	private String redirectEmailAddress;
	
	
	@GUI(detail = { 
			@Detail(context = SettingsView.class, order = 6, type=RichTextArea.class), 
	})
	private String passwordResetTemplate;
	
	@GUI(detail = { 
			@Detail(context = SettingsView.class, order = 7, type=RichTextArea.class), 
	})
	private String weeklyEvaluationTemplate;
	
	private long id;
	
}
