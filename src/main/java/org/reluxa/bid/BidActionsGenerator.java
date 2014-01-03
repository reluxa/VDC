package org.reluxa.bid;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.reluxa.player.Player;
import org.reluxa.player.service.AbstractEvent;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.reluxa.vaadin.widget.AbstractColumnGenerator;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;

public class BidActionsGenerator extends AbstractColumnGenerator<Bid, Component> {

	@Inject 
	BeanManager bm;
	
	@Inject
	private VaadinAccessControl accessControl;

	@Override
	public Component generateCell(Bid bid) {
		Player player = accessControl.getCurrentPlayer();
		HorizontalLayout buttons = new HorizontalLayout();
		buttons.setSpacing(true);
		
		if (isOwner(bid, player)) {
			buttons.addComponent(createEventButton("Delete",new DeleteBidEvent(bid)));
		}
		
		if (isPartner(bid, player)) {
			buttons.addComponent(createEventButton("Accept",new AcceptBidEvent(bid)));
		}
		
		return buttons;
	}


	private Button createEventButton(String caption, final AbstractEvent sendEvent) {
		Button deleteButton = new Button(caption);
		deleteButton.setStyleName("small");

		deleteButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("Event fired");
				bm.fireEvent(sendEvent);
			}
		});
		return deleteButton;
	}
	
	private boolean isOwner(Bid bid, Player player) {
		return bid.getCreator().equals(player);
	}
	
	private boolean isPartner(Bid bid, Player player) {
		return player.equals(bid.getPartner());
	}


}
