package org.reluxa.bid;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

import org.reluxa.model.IDObject;
import org.reluxa.player.Player;
import org.reluxa.vaadin.annotation.GUI;
import org.reluxa.vaadin.annotation.Table;

import com.db4o.config.annotations.Indexed;

@Data
public class Bid implements IDObject {
	
	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 1)
	})
	@Indexed
	private UUID uid;

	@GUI( table = {
		@Table(context = CurrentWeekBidView.class, order = 2)
	})
	private String status;

	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 3)
	})
	private String type;

	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 4)
	})
	private Date creationTime;

	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 5, type=CreatorColumnGenerator.class)
	})
	private Player creator;

	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 6, type=PartnerColumnGenerator.class)
	})
	private Player partner;
	
	@GUI( table = {
			@Table(context = CurrentWeekBidView.class, order = 7, type=BidActionsGenerator.class)
	})
	public transient String action;
	
  private transient long id;

}
