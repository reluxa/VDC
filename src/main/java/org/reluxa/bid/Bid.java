package org.reluxa.bid;

import java.util.Date;

import lombok.Data;

import org.reluxa.bid.view.BidActionsGenerator;
import org.reluxa.bid.view.BidHistoryView;
import org.reluxa.bid.view.BidScoreGenerator;
import org.reluxa.bid.view.CreatorColumnGenerator;
import org.reluxa.bid.view.CurrentWeekBidView;
import org.reluxa.bid.view.PartnerColumnGenerator;
import org.reluxa.bid.view.StatusGenerator;
import org.reluxa.bid.view.TicketView;
import org.reluxa.model.IDObject;
import org.reluxa.player.Player;
import org.reluxa.vaadin.annotation.GUI;
import org.reluxa.vaadin.annotation.Table;

import com.db4o.config.annotations.Indexed;

@Data
public class Bid implements IDObject {
	
	@GUI(table = {
			@Table(context = CurrentWeekBidView.class, order = 1),
			@Table(context = BidHistoryView.class, order = 1),
			@Table(context = TicketView.class, order = 1)
	})
	private long id;

	@GUI(table = { 
			@Table(context = CurrentWeekBidView.class, order = 2, type = StatusGenerator.class),
			@Table(context = BidHistoryView.class, order = 2, type = StatusGenerator.class),
	})
	@Indexed
	private String status;

	private String type;

	@GUI(table = { 
			@Table(context = CurrentWeekBidView.class, order = 4),
			@Table(context = BidHistoryView.class, order = 4),
			@Table(context = TicketView.class, order = 3)
	})
	@Indexed
	private Date creationTime;

	@GUI(table = { 
			@Table(context = CurrentWeekBidView.class, order = 5, type = CreatorColumnGenerator.class),
			@Table(context = BidHistoryView.class, order = 5, type = CreatorColumnGenerator.class),
			@Table(context = TicketView.class, order = 4, type = CreatorColumnGenerator.class),
	})
	private Player creator;

	@GUI(table = { 
			@Table(context = CurrentWeekBidView.class, order = 6, type = PartnerColumnGenerator.class),
			@Table(context = BidHistoryView.class, order = 6, type = PartnerColumnGenerator.class),
			@Table(context = TicketView.class, order = 5, type = PartnerColumnGenerator.class),
	})
	private Player partner;

	@GUI(table = { @Table(context = CurrentWeekBidView.class, order = 7, type = BidActionsGenerator.class) })
	private transient String action;

	@GUI(table = { @Table(context = CurrentWeekBidView.class, order = 1, type = BidScoreGenerator.class) })
	private transient Double score;
	
	@GUI(table = { 
			@Table(context = TicketView.class, order = 2) 
	})
	private String ticketCode;
	
}
