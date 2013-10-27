package org.reluxa;

import javax.inject.Inject;

import com.vaadin.cdi.CDIUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@CDIUI
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
	
	@Inject
	LoginController loginController;
	
    Navigator navigator;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Navigation Example");
        navigator = new Navigator(this, this);
        navigator.addView("", new LoginView());
        loginController.sayHello();
    }

}
