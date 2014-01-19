package org.reluxa.bid.event;

import lombok.Data;

import org.reluxa.AbstractEvent;
import org.reluxa.bid.Bid;

@Data
public class BidModelChanged extends AbstractEvent {
	
	private Bid created;
	
	private Bid[] deleted;
	
	private Bid[] updated;

}
