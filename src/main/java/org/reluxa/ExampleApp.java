package org.reluxa;

import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.internal.BeanStoreContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Extension;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

@CDIUI
@SuppressWarnings("serial")
@Theme("mytheme")
@Widgetset("org.reluxa.AppWidgetSet")
@VaadinServletConfiguration(productionMode = false, ui = ExampleApp.class, closeIdleSessions=true)
public class ExampleApp extends UI {
	
	@Inject
	private CDIViewProvider viewProvider;
	
	@Inject
	private BeanStoreContainer beanStoreContainer;

	Navigator navigator;
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("BLHSE Squash");
		navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
	} 

	
	@Override
	public void addExtension(Extension extension) {
	  // TODO Auto-generated method stub
	  super.addExtension(extension);
	}
	
	
	@Override
	public void removeExtension(Extension extension) {
	  // TODO Auto-generated method stub
	  super.removeExtension(extension);
	}
		
	@Override
	public void detach() {
		beanStoreContainer.getUIBeanStore(this.getUIId()).dereferenceAllBeanInstances();
		UI.getCurrent().close();
		UI.getCurrent().getPage().setLocation(VaadinService.getCurrentRequest().getContextPath()+"/VAADIN/logout.html");
		VaadinSession.getCurrent().close();
		viewProvider = null;
		navigator = null;
	}
	

}
