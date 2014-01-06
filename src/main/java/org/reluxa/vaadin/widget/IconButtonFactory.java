package org.reluxa.vaadin.widget;

import com.vaadin.ui.Button;

public class IconButtonFactory {
	
	public static Button get(String caption, String icon) {
		Button button = new Button();
		button.setHtmlContentAllowed(true);
		button.setCaption(Icon.get(icon)+caption);
		return button;
	}

}
