package org.reluxa;

import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@CDIUI
@SuppressWarnings("serial")
@Theme("mytheme")
public class ExampleApp extends UI {

	@Inject
	private CDIViewProvider viewProvider;

	Navigator navigator;

	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("BLHSE Squash");
		navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
	}
	
	

}
