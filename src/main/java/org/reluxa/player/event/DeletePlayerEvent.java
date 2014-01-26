package org.reluxa.player.event;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.reluxa.AbstractEvent;
import org.reluxa.player.Player;

@Data
@RequiredArgsConstructor
public class DeletePlayerEvent extends AbstractEvent {
	
	@NonNull
	private Player player;

}
