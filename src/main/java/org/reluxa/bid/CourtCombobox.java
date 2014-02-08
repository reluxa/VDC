package org.reluxa.bid;

import java.util.Arrays;

import com.vaadin.ui.ComboBox;

public class CourtCombobox extends ComboBox {

	public CourtCombobox() {
		super("",Arrays.asList("Gold center","BME Sportközpont"));
		setNullSelectionAllowed(false);
		setTextInputAllowed(false);
	}
	
}
