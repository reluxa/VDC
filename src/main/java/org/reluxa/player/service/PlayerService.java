package org.reluxa.player.service;

import java.util.Collection;
import java.util.List;

import javax.enterprise.event.Observes;

import org.reluxa.AbstractService;
import org.reluxa.Log;
import org.reluxa.player.Player;

import com.db4o.ObjectSet;

@Log
public class PlayerService extends AbstractService implements PlayerServiceIF {

  @Override
  public Collection<Player> getAllPlayers() {
    ObjectSet<Player> players = db.query(Player.class);

    List<Player> pl = players.subList(0, players.size());
    for (Player player : pl) {
      log.debug(db.ext().getID(player) + "\t" + player);
    }
    return pl;
  }

  @Override
  public void createUser(Player user) throws DuplicateUserException {
    if (hasNoDuplicates(user)) {
      db.store(user);
    } else {
      throw new DuplicateUserException();
    }
  }

  @Override
  public boolean hasNoDuplicates(Player user) {
    Player temp = new Player();
    temp.setEmail(user.getEmail());
    return db.queryByExample(temp).size() == 0;
  }

  @Override
  public void deletePlayer(@Observes DeletePlayerEvent deletePlayerEvent) {
    ObjectSet<Player> players = db.queryByExample(deletePlayerEvent.getPlayer());
    if (players.size() != 1) {
      throw new RuntimeException("Invalid object for delete:" + deletePlayerEvent.getPlayer());
    }
    db.delete(players.get(0));
  }

  @Override
  public void updateUser(Player bean) {
    db.ext().bind(bean, bean.getId());
    db.store(bean);
  }

}
