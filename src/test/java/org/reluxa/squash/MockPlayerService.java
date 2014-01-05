package org.reluxa.squash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.event.Observes;

import org.apache.commons.lang.NotImplementedException;
import org.reluxa.player.Player;
import org.reluxa.player.service.DeletePlayerEvent;
import org.reluxa.player.service.DuplicateUserException;
import org.reluxa.player.service.PlayerServiceIF;

public class MockPlayerService implements PlayerServiceIF {

	public List<Player> players = new ArrayList<>();

	@Override
	public Collection<Player> getAllPlayers() {
		return players;
	}

	@Override
	public void createUser(Player user) throws DuplicateUserException {
		players.add(user);
	}

	@Override
	public boolean hasNoDuplicates(Player user) {
		throw new NotImplementedException();
	}

	@Override
	public void deletePlayer(@Observes DeletePlayerEvent deletePlayerEvent) {
		throw new NotImplementedException();
	}

	@Override
	public void updateUser(Player bean) {
		throw new NotImplementedException();
	}

}
