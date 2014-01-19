package org.reluxa.vaadin.widget;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.DateField;


public class TimeField extends DateField {

	public TimeField() {
		System.out.println("Constructor called");
		setResolution(Resolution.MINUTE);
	}
	
}
