package org.reluxa.vaadin.widget;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

public class SimpleNavigationButton extends Button {
	
	/**
	 * 
	 */
  private static final long serialVersionUID = 1L;

	public SimpleNavigationButton(String caption, final String target) {
		super(caption);
		
		addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(target);
			}
		});
	}
	
	

}
