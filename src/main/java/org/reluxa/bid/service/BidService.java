package org.reluxa.bid.service;

import java.util.Collection;
import java.util.Date;

import javax.enterprise.event.Observes;

import org.reluxa.AbstractService;
import org.reluxa.bid.AcceptBidEvent;
import org.reluxa.bid.Bid;
import org.reluxa.bid.BidModelChanged;
import org.reluxa.bid.BidStatus;
import org.reluxa.bid.DeleteBidEvent;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

public class BidService extends AbstractService implements BidServiceIF {

	/* (non-Javadoc)
	 * @see org.reluxa.bid.service.BidServiceIF#createBid(org.reluxa.bid.Bid)
	 */
	@Override
  public void createBid(Bid bid) {
		System.out.println("from create"+bid);
		
		System.out.println("my id"+db.ext().getID(bid.getCreator()));
		System.out.println("partner id"+db.ext().getID(bid.getPartner()));
		
		
		db.store(bid);
		BidModelChanged created = new BidModelChanged();
		created.setCreated(bid);
		beanManager.fireEvent(created);
	}

	/* (non-Javadoc)
	 * @see org.reluxa.bid.service.BidServiceIF#getAll()
	 */
	@Override
  public Collection<Bid> getAll() {
		ObjectSet<Bid> bids = db.query(Bid.class);
		return bids.subList(0, bids.size());
	}

	/* (non-Javadoc)
	 * @see org.reluxa.bid.service.BidServiceIF#bidAccepted(org.reluxa.bid.AcceptBidEvent)
	 */
	@Override
  public void bidAccepted(@Observes AcceptBidEvent event) {
		Bid bid = event.getBid();
//		System.out.println(bid.getId());
//		Bid original = db.ext().getByID(bid.getId());
//		System.out.println(original);
//		
//		

		
//		db.ext().bind(bid, bid.getId());
		bid.setStatus(BidStatus.PENDING.toString());
		db.store(bid);

		BidModelChanged mchanged = new BidModelChanged();
		mchanged.setUpdated(new Bid[] {bid});
		beanManager.fireEvent(mchanged);
	}
	
	/* (non-Javadoc)
	 * @see org.reluxa.bid.service.BidServiceIF#delete(org.reluxa.bid.DeleteBidEvent)
	 */
	@Override
  public void delete(@Observes DeleteBidEvent deleteBidEvent) {
		Bid bid = deleteBidEvent.getBid();
		db.ext().bind(bid, bid.getId());
		db.delete(bid);

		BidModelChanged mchanged = new BidModelChanged();
		mchanged.setDeleted(new Bid[] {bid});
		beanManager.fireEvent(mchanged);
	}

	@Override
  public Collection<Bid> getAllBids(final Date from) {
		ObjectSet<Bid> bids = db.query(new Predicate<Bid>(){
			@Override
      public boolean match(Bid bid) {
				return from.before(bid.getCreationTime());
      }});
		return bids.subList(0, bids.size());
  }

}
