package org.reluxa.bid.service;

import java.util.Collection;

import javax.enterprise.event.Observes;

import org.reluxa.AbstractService;
import org.reluxa.bid.AcceptBidEvent;
import org.reluxa.bid.Bid;
import org.reluxa.bid.BidModelChanged;
import org.reluxa.bid.BidStatus;
import org.reluxa.bid.DeleteBidEvent;

import com.db4o.ObjectSet;

public class BidService extends AbstractService {

	public void createBid(Bid bid) {
		db.store(bid);
		BidModelChanged created = new BidModelChanged();
		created.setCreated(bid);
		beanManager.fireEvent(created);
	}

	public Collection<? extends Bid> getAll() {
		ObjectSet<Bid> bids = db.query(Bid.class);
		return bids.subList(0, bids.size());
	}

	public void bidAccepted(@Observes AcceptBidEvent event) {
		Bid bid = event.getBid();
		System.out.println(bid.getId());
		Bid original = db.ext().getByID(bid.getId());
		System.out.println(original);
		
		
		bid.setStatus(BidStatus.PENDING.toString());
		db.ext().bind(bid, bid.getId());
		db.store(bid);

		BidModelChanged mchanged = new BidModelChanged();
		mchanged.setUpdated(new Bid[] {bid});
		beanManager.fireEvent(mchanged);
	}
	
	public void delete(@Observes DeleteBidEvent deleteBidEvent) {
		Bid bid = deleteBidEvent.getBid();
		db.ext().bind(bid, bid.getId());
		db.delete(bid);

		BidModelChanged mchanged = new BidModelChanged();
		mchanged.setDeleted(new Bid[] {bid});
		beanManager.fireEvent(mchanged);
	}

}
