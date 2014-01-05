package org.reluxa.player.service;

import java.util.Collection;

import javax.enterprise.event.Observes;

import org.reluxa.player.Player;

public interface PlayerServiceIF {

	public abstract Collection<Player> getAllPlayers();

	public abstract void createUser(Player user) throws DuplicateUserException;

	public abstract boolean hasNoDuplicates(Player user);

	public abstract void deletePlayer(@Observes DeletePlayerEvent deletePlayerEvent);

	public abstract void updateUser(Player bean);

}