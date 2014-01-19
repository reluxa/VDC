package org.reluxa.bid.event;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.reluxa.AbstractEvent;
import org.reluxa.bid.Bid;

@Data
@RequiredArgsConstructor
public class DeleteBidEvent extends AbstractEvent {
	
	@NonNull
	private Bid bid;

}
