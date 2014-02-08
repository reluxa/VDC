package org.reluxa.bid.view;

import javax.annotation.PostConstruct;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

@CDIView(TicketValidationResultView.VIEW_NAME)
public class TicketValidationResultView extends VerticalLayout implements View {

	public static final String VIEW_NAME = "validationresult";
	
	@PostConstruct
	public void init() {
		setStyleName("root");
		setSizeFull();
		Label label = new Label("<h1><font color='green'>Ticket code successfully validated!</font></h1>", ContentMode.HTML);
		label.setWidth(null);
		label.setHeight(null);
		addComponent(label);
		setComponentAlignment(label, Alignment.MIDDLE_CENTER);
	}
	
	
	@Override
  public void enter(ViewChangeEvent event) {
	  
  }


}
