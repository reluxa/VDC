package org.reluxa.bid.service;

import java.util.Collection;
import java.util.Date;

import javax.enterprise.event.Observes;

import org.reluxa.bid.AcceptBidEvent;
import org.reluxa.bid.Bid;
import org.reluxa.bid.DeleteBidEvent;

public interface BidServiceIF {

	public abstract void createBid(Bid bid);

	public abstract Collection<Bid> getAll();
	
	public abstract Collection<Bid> getAllBids(Date from);

	public abstract void bidAccepted(@Observes AcceptBidEvent event);

	public abstract void delete(@Observes DeleteBidEvent deleteBidEvent);

}