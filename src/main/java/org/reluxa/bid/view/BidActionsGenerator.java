package org.reluxa.bid.view;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.reluxa.AbstractEvent;
import org.reluxa.bid.Bid;
import org.reluxa.bid.BidStatus;
import org.reluxa.bid.event.AcceptBidEvent;
import org.reluxa.bid.event.DeleteBidEvent;
import org.reluxa.player.Player;
import org.reluxa.vaadin.auth.VaadinAccessControl;
import org.reluxa.vaadin.widget.AbstractColumnGenerator;
import org.reluxa.vaadin.widget.IconButtonFactory;

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
			buttons.addComponent(createEventButton("Delete","remove2",new DeleteBidEvent(bid)));
		}
		
		if (isPartner(bid, player) && isWaitingForApproval(bid)) {
			buttons.addComponent(createEventButton("Accept","checkmark2",new AcceptBidEvent(bid)));
		}
		
		return buttons;
	}


	private Button createEventButton(String caption, String icon,  final AbstractEvent sendEvent) {
		Button deleteButton = IconButtonFactory.get(caption, icon);
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
	
	
	private boolean isWaitingForApproval(Bid bid) {
		return BidStatus.WAITING_FOR_APPOVAL.toString().equals(bid.getStatus());
	}
	
	
	private boolean isOwner(Bid bid, Player player) {
	  	return true;
		//return bid.getCreator().equals(player);
	}
	
	private boolean isPartner(Bid bid, Player player) {
	  	return true;
		//return player.equals(bid.getPartner());
	}


}
