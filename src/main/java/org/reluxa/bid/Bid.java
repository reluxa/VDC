package org.reluxa.bid;

import java.util.Date;

import lombok.Data;

import org.reluxa.model.IDObject;
import org.reluxa.player.Player;
import org.reluxa.vaadin.annotation.GUI;
import org.reluxa.vaadin.annotation.Table;

@Data
public class Bid implements IDObject {

	public static enum BidStatus {
		WAITING_FOR_APPOVAL,
		PENDING, 
		LOST, 
		WON
	}
	
	public static enum BidType {
		SINGLE,
		WITH_FRIEND
	}

	@GUI( table = {
		@Table(context = CurrentWeekBidView.class, order = 1)
	})
	private BidStatus status;

	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 2)
	})
	private BidType type;

	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 3)
	})
	private Date creationTime;

	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 4, type=CreatorColumnGenerator.class)
	})
	private Player creator;

	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 5, type=PartnerColumnGenerator.class)
	})
	private Player partner;
	
	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 6, type=BidActionsGenerator.class)
	})
	public transient String action;
	
  private transient long id;

}
