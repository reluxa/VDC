package org.reluxa.bid;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.reluxa.player.service.AbstractEvent;

@Data
@RequiredArgsConstructor
public class DeleteBidEvent extends AbstractEvent {
	
	@NonNull
	private Bid bid;

}
