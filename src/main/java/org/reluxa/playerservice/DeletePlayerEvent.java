package org.reluxa.playerservice;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import org.reluxa.model.Player;

@Data
@RequiredArgsConstructor
public class DeletePlayerEvent extends AbstractEvent {
	
	@NonNull
	private Player player;

}
