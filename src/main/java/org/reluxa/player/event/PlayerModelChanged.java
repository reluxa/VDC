package org.reluxa.player.event;

import lombok.Data;

import org.reluxa.AbstractEvent;
import org.reluxa.player.Player;

@Data
public class PlayerModelChanged extends AbstractEvent {
	
	private Player created;
	
	private Player[] deleted;
	
	private Player[] updated;

}
