package org.reluxa.squash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.enterprise.event.Observes;

import org.reluxa.bid.Bid;
import org.reluxa.bid.event.AcceptBidEvent;
import org.reluxa.bid.event.DeleteBidEvent;
import org.reluxa.bid.service.BidServiceIF;
import org.reluxa.player.Player;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class MockBidService implements BidServiceIF {

	public List<Bid> bids = new ArrayList<>();
	
	@Override
	public void createBid(Bid bid) {
		bids.add(bid);
  }

	@Override
  public Collection<Bid> getAll() {
		return new ArrayList<>(bids);
	}

	@Override
  public Collection<Bid> getAllBids(final Date from) {
		return Collections2.filter(bids, new Predicate<Bid>() {
			@Override
      public boolean apply(@Nullable Bid bid) {
					return from.before(bid.getCreationTime());
      }
		});
  }

	@Override
  public void bidAccepted(@Observes AcceptBidEvent event) {
	  // TODO Auto-generated method stub
	  
  }

	@Override
  public void delete(@Observes DeleteBidEvent deleteBidEvent) {
	  // TODO Auto-generated method stub
	  
  }

	@Override
  public Collection<Bid> getBids(com.db4o.query.Predicate<Bid> predicate) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Collection<Bid> getAllNotEvaluatedBids() {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public void updateAll(Collection<Bid> bids) {
	  // TODO Auto-generated method stub
	  
  }

	@Override
  public Collection<Bid> getAllBids(Date from, Date to) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Collection<Bid> getTicketsForPlayer(Player player) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public boolean validateTicket(Bid bid) {
	  // TODO Auto-generated method stub
	  return false;
  }

}
