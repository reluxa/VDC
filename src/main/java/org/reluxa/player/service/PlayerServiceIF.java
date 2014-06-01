package org.reluxa.player.service;

import java.util.Collection;

import javax.enterprise.event.Observes;

import org.reluxa.player.Player;
import org.reluxa.player.event.DeletePlayerEvent;

public interface PlayerServiceIF {

	Collection<Player> getAllPlayers();

	void createUser(Player user) throws DuplicateUserException;

	boolean hasNoDuplicates(Player user);

	void deletePlayer(@Observes DeletePlayerEvent deletePlayerEvent);

	void updateUser(Player bean);
	
	void updateUser(Collection<Player> players);

}