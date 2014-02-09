package org.reluxa.login;

import javax.inject.Inject;

import org.reluxa.bid.Ticket;
import org.reluxa.bid.service.BidServiceIF;
import org.reluxa.bid.view.TicketValidationResultView;
import org.reluxa.vaadin.widget.GeneratedForm;
import org.reluxa.vaadin.widget.IconButtonFactory;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class TicketValidation extends VerticalLayout implements ClickListener {

	private GeneratedForm<Ticket> detailForm = new GeneratedForm<>(Ticket.class, TicketValidation.class);
	
	private Ticket ticket;
	
	@Inject
	private BidServiceIF bidService;
	
	final Button validationButton;
	
	public TicketValidation() {
    Label label = new Label("<h2>Ticket validation<h2>", ContentMode.HTML);
    validationButton = IconButtonFactory.get("Validate", "question");
    validationButton.addClickListener(this);
    addComponent(label);
    addComponent(detailForm);
    addComponent(validationButton);
	}
	
	public void reset() {
		ticket = new Ticket();
		detailForm.setBean(ticket);
	}

	@Override
  public void buttonClick(ClickEvent event) {
		if (!detailForm.isValid()) {
			Notification.show("Invalid data in form!");
			return;
		}
		final Window confirmation = new Window("Confirmation");
		confirmation.setModal(true);
		confirmation.setResizable(false);
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		vl.setMargin(true);
		
		Label text = new Label("<h3>Please check all the information below are correct:</h3>",ContentMode.HTML);
		StringBuilder builder = new StringBuilder();
		builder.append("Ticket code: "+ticket.getTicketCode()+ "<br/>");
		builder.append("Price: "+ticket.getPrice()+ " HUF<br/>");
		builder.append("Duration: "+ticket.getDuration()+ " minutes<br/>");
		builder.append("Court: "+ticket.getCourt());
		
		Label info = new Label(builder.toString(), ContentMode.HTML);

		Button yes = new Button("Yes");
		yes.addClickListener(new ClickListener() { 
			@Override
			public void buttonClick(ClickEvent event) {
				if (bidService.validateTicket(ticket)) {
					confirmation.close();
					UI.getCurrent().getNavigator().navigateTo(TicketValidationResultView.VIEW_NAME);
				} else {
					Notification.show("Invalid ticket code!", Type.ERROR_MESSAGE);
				}
			}
		});
		Button no = new Button("No");
		no.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				confirmation.close();
			}
		});
		HorizontalLayout buttonLine = new HorizontalLayout(yes,no);
		buttonLine.setSpacing(true);
		
		
		vl.addComponent(text);
		vl.addComponent(info);
		vl.addComponent(buttonLine);
		vl.setComponentAlignment(buttonLine, Alignment.MIDDLE_CENTER);
		
		confirmation.setContent(vl);
		UI.getCurrent().addWindow(confirmation);
		confirmation.center();
  }

}
