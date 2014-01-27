package org.reluxa.message;

import java.util.Collection;

import org.reluxa.player.Player;

public interface MessageServiceIF {

	public boolean sendMessageOnBehalfOfCurrentUser(Collection<Player> recipients, String text);
	
}