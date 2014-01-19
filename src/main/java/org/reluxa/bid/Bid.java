package org.reluxa.bid;

import java.util.Date;

import lombok.Data;

import org.reluxa.bid.view.BidActionsGenerator;
import org.reluxa.bid.view.BidScoreGenerator;
import org.reluxa.bid.view.CreatorColumnGenerator;
import org.reluxa.bid.view.CurrentWeekBidView;
import org.reluxa.bid.view.PartnerColumnGenerator;
import org.reluxa.bid.view.StatusGenerator;
import org.reluxa.model.IDObject;
import org.reluxa.player.Player;
import org.reluxa.vaadin.annotation.GUI;
import org.reluxa.vaadin.annotation.Table;

import com.db4o.config.annotations.Indexed;

@Data
public class Bid implements IDObject {
	
	@GUI(table = { @Table(context = CurrentWeekBidView.class, order = 1) })
	@Indexed
	private transient long id;

	@GUI(table = { @Table(context = CurrentWeekBidView.class, order = 2, type = StatusGenerator.class) })
	private String status;

//	@GUI(table = { @Table(context = CurrentWeekBidView.class, order = 3) })
	private String type;

	@GUI(table = { @Table(context = CurrentWeekBidView.class, order = 4) })
	@Indexed
	private Date creationTime;

	@GUI(table = { @Table(context = CurrentWeekBidView.class, order = 5, type = CreatorColumnGenerator.class) })
	private Player creator;

	@GUI(table = { @Table(context = CurrentWeekBidView.class, order = 6, type = PartnerColumnGenerator.class) })
	private Player partner;

	@GUI(table = { @Table(context = CurrentWeekBidView.class, order = 7, type = BidActionsGenerator.class) })
	private transient String action;

	@GUI(table = { @Table(context = CurrentWeekBidView.class, order = 1, type = BidScoreGenerator.class) })
	private transient Double score;
	
	private String ticketCode;
	
}
