package org.reluxa;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.ContextNotActiveException;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

@CDIUI
@SuppressWarnings("serial")
@Theme("mytheme")
@Widgetset("org.reluxa.AppWidgetSet")
@VaadinServletConfiguration(productionMode = false, ui = ExampleApp.class, closeIdleSessions=true)
public class ExampleApp extends UI {
	
  protected Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@Inject
	private CDIViewProvider viewProvider;
	
	@Inject
	private BeanStoreContainer beanStoreContainer;

	Navigator navigator;
	
	@Override
	protected void init(VaadinRequest request) {
		getPage().setTitle("BLHSE Squash & Badminton");
		navigator = new Navigator(this, this);
		navigator.addProvider(viewProvider);
	}
	
	public String getVersion() {
    String result = "build version";
    try {
    	Properties prop = new Properties();
			prop.load(VaadinServlet.getCurrent().getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF"));
			result = prop.getProperty("build-version") +" "+ prop.getProperty("build-revision")+ " "+prop.getProperty("build-time"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
    return result;
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
		try {
			beanStoreContainer.getUIBeanStore(this.getUIId()).dereferenceAllBeanInstances();
		} catch (ContextNotActiveException ex) {
			//swallow
		}
		try {
		UI.getCurrent().close();
		//UI.getCurrent().getPage().setLocation(VaadinService.getCurrentRequest().getContextPath()+"/VAADIN/logout.html");
		UI.getCurrent().getPage().setLocation(VaadinService.getCurrentRequest().getContextPath());
		VaadinSession.getCurrent().close();
		} catch (Exception ex) {}
		viewProvider = null;
		navigator = null;
	}
	

}
