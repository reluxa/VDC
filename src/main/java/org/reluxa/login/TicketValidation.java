package org.reluxa.login;

import javax.inject.Inject;

import org.reluxa.bid.Bid;
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

	private GeneratedForm<Bid> detailForm = new GeneratedForm<>(Bid.class, TicketValidation.class);
	
	private Bid bidTicket;
	
	@Inject
	private BidServiceIF bidService;
	
	public TicketValidation() {
    Label label = new Label("<h2>Enter ticket validation information<h2>", ContentMode.HTML);
    Button button = IconButtonFactory.get("Validate", "question");
    button.addClickListener(this);
    addComponent(label);
    addComponent(detailForm);
    addComponent(button);
	}
	
	public void reset() {
		bidTicket = new Bid();
		detailForm.setBean(bidTicket);
	}

	@Override
  public void buttonClick(ClickEvent event) {
		final Window confirmation = new Window("Confirmation");
		confirmation.setModal(true);
		confirmation.setResizable(false);
		VerticalLayout vl = new VerticalLayout();
		vl.setSpacing(true);
		vl.setMargin(true);
		
		Label text = new Label("<h3>Please check that all the information below are correct:</h3>",ContentMode.HTML);
		StringBuilder builder = new StringBuilder();
		builder.append("Ticket code: "+bidTicket.getTicketCode()+ "<br/>");
		builder.append("Price: "+bidTicket.getPrice()+ " HUF<br/>");
		builder.append("Duration: "+bidTicket.getDuration()+ " minutes<br/>");
		builder.append("Court: "+bidTicket.getCourt());
		
		Label info = new Label(builder.toString(), ContentMode.HTML);

		Button yes = new Button("Yes");
		yes.addClickListener(new ClickListener() { 
			@Override
			public void buttonClick(ClickEvent event) {
				if (bidService.validateTicket(bidTicket)) {
					confirmation.close();
					UI.getCurrent().getNavigator().navigateTo(TicketValidationResultView.VIEW_NAME);
				} else {
					Notification.show("Invalid ticket code!",Type.ERROR_MESSAGE);
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
