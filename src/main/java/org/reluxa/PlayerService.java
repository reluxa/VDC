package org.reluxa;
import java.util.Collection;

import org.reluxa.model.Player;

import com.db4o.ObjectSet;


public class PlayerService extends AbstractService {

	
	public Collection<Player> getAllPlayers() {
		ObjectSet<Player> players =  db.query(Player.class);
		return players.subList(0, players.size());
	}
	
	public void createUser(Player user) throws DuplicateUserException {
		if (hasNoDuplicates(user)) {
			db.store(user);
		} else {
			throw new DuplicateUserException();
		}
	}

	public boolean hasNoDuplicates(Player user) {
		Player temp = new Player();
		temp.setEmail(user.getEmail());
		return db.queryByExample(temp).size() == 0;
	}
	
}
