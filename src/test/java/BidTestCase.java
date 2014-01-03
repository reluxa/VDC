import java.util.Date;

import org.reluxa.bid.Bid;
import org.reluxa.db.DBConnectionFactory;

import com.db4o.ObjectContainer;


public class BidTestCase {

	DBConnectionFactory factory = new DBConnectionFactory();
	
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
	
}
