import java.util.Date;

import org.junit.Test;
import org.reluxa.bid.Bid;
import org.reluxa.db.DBConnectionFactory;

import com.db4o.ObjectContainer;


public class BidTestCase {

	DBConnectionFactory factory = new DBConnectionFactory();
	
	@Test
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
		
		
		Bid bid2 = session2.ext().getByUUID(bid.getDb4ouuid());
		System.out.println(bid2);

		session2.commit(); session2.close();
	}
	
}
