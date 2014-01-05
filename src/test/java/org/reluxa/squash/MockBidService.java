package org.reluxa.squash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.enterprise.event.Observes;

import org.reluxa.bid.AcceptBidEvent;
import org.reluxa.bid.Bid;
import org.reluxa.bid.DeleteBidEvent;
import org.reluxa.bid.service.BidServiceIF;

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

}
