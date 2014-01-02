package org.reluxa.bid;

import lombok.Data;

import org.reluxa.player.service.AbstractEvent;

@Data
public class BidModelChanged extends AbstractEvent {
	
	private Bid created;
	
	private Bid[] deleted;
	
	private Bid[] updated;

}
