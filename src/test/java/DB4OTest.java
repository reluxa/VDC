import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.reluxa.bid.Bid;
import org.reluxa.db.DBConnectionFactory;
import org.reluxa.player.Player;
import org.reluxa.vaadin.util.RequirsiveActivator;

import com.db4o.ObjectContainer;


public class DB4OTest {

	DBConnectionFactory factory = new DBConnectionFactory(System.getProperty("java.io.tmpdir")+"/"+System.currentTimeMillis()+".db");
	
	public void bidTestCase() {
		ObjectContainer root = factory.getConnection();

		ObjectContainer session = root.ext().openSession();
		Bid bid = new Bid();
		bid.setCreationTime(new Date());
		
		session.store(bid);
		
		Bid bidafter = session.ext().getByID(bid.getId());
		System.out.println(bidafter);

		
		session.commit(); session.close();

		
		System.out.println(bid.getId());
		
		ObjectContainer session2 = root.ext().openSession();
		Bid bid2 = session2.ext().getByID(bid.getId());
		session2.ext().activate(bid2);
				
		System.out.println(bid2);

		session2.commit(); session2.close();
	}
	
	@Test
	public void bidSaveTest() {
		ObjectContainer root = factory.getConnection();
		
		//Preparation
		ObjectContainer db = root.ext().openSession();
		Player player1 = new Player();
		player1.setFullName("Test 1");
		player1.setEmail("test1@email.com");

		Player player2 = new Player();
		player2.setFullName("Test 1");
		player2.setEmail("test1@email.com");
		
		db.store(player1);
		db.store(player2);
		db.commit(); db.close();

		//just to make sure it's 
		db = root.ext().openSession();
		List<Player> players = Arrays.asList(db.query(Player.class).toArray(new Player[]{}));
		System.out.println(players.size());
		db.commit(); db.close();
		
		
		db = root.ext().openSession();
		Bid bid = new Bid();
		bid.setStatus("status");

//		db.ext().bind(players.get(0),players.get(0).getId());
//		db.ext().bind(players.get(1),players.get(1).getId());
		
		bid.setCreator(players.get(0));
		bid.setPartner(players.get(1));
		
		RequirsiveActivator.activate(bid, db);
		
		db.store(bid);
		db.commit(); db.close();

		
		db = root.ext().openSession();
		players = Arrays.asList(db.query(Player.class).toArray(new Player[]{}));
		Assert.assertEquals(2,players.size());
		db.commit(); db.close();
	}
	
}

