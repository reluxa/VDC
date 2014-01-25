package org.reluxa.bid.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.enterprise.event.Observes;

import org.reluxa.AbstractService;
import org.reluxa.Log;
import org.reluxa.bid.Bid;
import org.reluxa.bid.BidStatus;
import org.reluxa.bid.event.AcceptBidEvent;
import org.reluxa.bid.event.BidModelChanged;
import org.reluxa.bid.event.DeleteBidEvent;
import org.reluxa.player.Player;

import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

@Log
//@BidCache
public class BidService extends AbstractService implements BidServiceIF {

	@Override
	public void createBid(Bid bid) {
		db.store(bid);
		BidModelChanged created = new BidModelChanged();
		created.setCreated(bid);
		beanManager.fireEvent(created);
	}

	@Override
	public Collection<Bid> getAll() {
		ObjectSet<Bid> bids = db.query(Bid.class);
		return bids.subList(0, bids.size());
	}
	
	
	public void updateAll(Collection<Bid> bids) {
		//BidModelChanged mchanged = new BidModelChanged();
		
		for (Bid bid : bids) {
	    db.store(bid);
    }
		
		//mchanged.setUpdated(bids.toArray(new Bid[]{}));
		//beanManager.fireEvent(mchanged);
	}

	@Override
	public void bidAccepted(@Observes AcceptBidEvent event) {
		Bid bid = event.getBid();

		bid.setStatus(BidStatus.PENDING.toString());
		db.store(bid);

		BidModelChanged mchanged = new BidModelChanged();
		mchanged.setUpdated(new Bid[] { bid });
		beanManager.fireEvent(mchanged);
	}

	@Override
	public void delete(@Observes DeleteBidEvent deleteBidEvent) {
		Bid bid = deleteBidEvent.getBid();
		db.ext().bind(bid, bid.getId());
		db.delete(bid);

		BidModelChanged mchanged = new BidModelChanged();
		mchanged.setDeleted(new Bid[] { bid });
		beanManager.fireEvent(mchanged);
	}

	@Override
	public Collection<Bid> getAllBids(final Date from) {
		return getBids(new Predicate<Bid>() {
			@Override
			public boolean match(Bid bid) {
				return from.before(bid.getCreationTime());
			}
		});
	}
	
	@Override
	public Collection<Bid> getAllBids(final Date from, final Date to) {
		return getBids(new Predicate<Bid>() {
			@Override
			public boolean match(Bid bid) {
				return from.before(bid.getCreationTime()) && to.after(bid.getCreationTime());
			}
		});
	}
	
	@Override
	public Collection<Bid> getTicketsForPlayer(final Player player) {
		return getBids(new Predicate<Bid>() {
			@Override
			public boolean match(Bid bid) {
				return (bid.getTicketCode() != null && (player.equals(bid.getCreator()) || player.equals(bid.getPartner())));
			}
		});
	}


	
	
	@Override
	public Collection<Bid> getAllNotEvaluatedBids() {
		return getBids(new Predicate<Bid>() {
			@Override
			public boolean match(Bid bid) {
				return "PENDING".equals(bid.getStatus());
			}
		});
	}


	@Override
	public Collection<Bid> getBids(Predicate<Bid> predicate) {
		return new ArrayList<Bid>(db.query(predicate));
	}

}
