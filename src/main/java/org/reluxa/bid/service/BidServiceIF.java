package org.reluxa.bid.service;

import java.util.Collection;
import java.util.Date;

import javax.enterprise.event.Observes;

import org.reluxa.bid.Bid;
import org.reluxa.bid.Ticket;
import org.reluxa.bid.event.AcceptBidEvent;
import org.reluxa.bid.event.DeleteBidEvent;
import org.reluxa.player.Player;

import com.db4o.query.Predicate;

public interface BidServiceIF {

	void createBid(Bid bid);

	Collection<Bid> getAll();
	
	Collection<Bid> getAllBids(Date from);
	
	Collection<Bid> getBids(Predicate<Bid> predicate);
	
	void updateAll(Collection<Bid> bids);

	void bidAccepted(@Observes AcceptBidEvent event);

	void delete(@Observes DeleteBidEvent deleteBidEvent);

	Collection<Bid> getAllNotEvaluatedBids();

	Collection<Bid> getAllBids(Date from, Date to);

	Collection<Bid> getTicketsForPlayer(Player player);
	
	boolean validateTicket(Ticket bid);

}