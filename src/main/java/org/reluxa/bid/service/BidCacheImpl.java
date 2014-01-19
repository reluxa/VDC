package org.reluxa.bid.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;

import org.reluxa.bid.Bid;

@RequestScoped
public class BidCacheImpl {
	
	Map<Date, Collection<Bid>> map = new HashMap<>();
	
//	public Collection<Bid> getAllBids(final Date from) {
//		return map.get(from);
//	}
	
}
