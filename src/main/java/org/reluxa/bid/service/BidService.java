package org.reluxa.bid.service;

import java.util.Collection;

import javax.enterprise.event.Observes;

import org.reluxa.AbstractService;
import org.reluxa.bid.Bid;
import org.reluxa.bid.BidModelChanged;
import org.reluxa.bid.DeleteBidEvent;

import com.db4o.ObjectSet;

public class BidService extends AbstractService {

	public void createBid(Bid bid) {
		db.store(bid);
		bid.setId(db.ext().getID(bid));
		BidModelChanged created = new BidModelChanged();
		created.setCreated(bid);
		beanManager.fireEvent(created);
	}

	public Collection<? extends Bid> getAll() {
		ObjectSet<Bid> bids = db.query(Bid.class);
		return bids.subList(0, bids.size());
	}

	public void delete(@Observes DeleteBidEvent deleteBidEvent) {
		//Bid bid2 = db.ext().getByID(deleteBidEvent.getBid().getId());
		//System.out.println(bid2);
		
		
		Bid bid = deleteBidEvent.getBid();
		db.ext().bind(bid, bid.getId());
		db.delete(bid);
//		System.out.println("Bid delete was called..");
//		ObjectSet<Bid> bids = db.queryByExample(deleteBidEvent.getBid());
//		if (bids.size() != 1) {
//			throw new RuntimeException("Invalid object for delete:" + deleteBidEvent.getBid());
//		}
//		db.delete(bids.get(0));

		BidModelChanged mchanged = new BidModelChanged();
		mchanged.setDeleted(new Bid[] {bid});
		beanManager.fireEvent(mchanged);
	}

}
