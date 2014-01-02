package org.reluxa.bid;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.reluxa.player.service.AbstractEvent;

@Data
@RequiredArgsConstructor
public class AcceptBidEvent extends AbstractEvent {
	
	@NonNull
	private Bid bid;


}
