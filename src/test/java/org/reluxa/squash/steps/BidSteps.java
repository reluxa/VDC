package org.reluxa.squash.steps;

import java.util.Collection;
import java.util.Date;

import org.jbehave.core.annotations.Given;
import org.reluxa.bid.Bid;
import org.reluxa.bid.service.BidServiceIF;
import org.reluxa.player.Player;
import org.reluxa.player.service.PlayerServiceIF;
import org.reluxa.squash.MockBidService;
import org.reluxa.squash.MockPlayerService;

public class BidSteps {
	
	private BidServiceIF bidService;
	private PlayerServiceIF playerService;
	
	public BidSteps(BidServiceIF bidService, PlayerServiceIF playerService) {
		this.bidService = bidService;
		this.playerService = playerService;
	}

	
	@Given("empty database")
	public void clearDatabase() {
		((MockBidService)bidService).bids.clear();
		((MockPlayerService)playerService).players.clear();
	}
	
	@Given(value = "a bid with id $id where the creator is $email at $date")
	public void createSingleBid(Integer id, String email, Date date) {
		Bid bid = new Bid();
		bid.setId(id);
		bid.setCreationTime(date);
		bid.setCreator(getPlayerByName(email));
		bidService.createBid(bid);
	}
	
	@Given(value = "a multi user bid with id $id where the creator is $email and partner is $email2 at $date")
	public void createWithPartner(Integer id, String email, String email2, Date date) {
		Bid bid = new Bid();
		bid.setId(id);
		bid.setCreationTime(date);
		bid.setCreator(getPlayerByName(email));
		bid.setPartner(getPlayerByName(email2));
		bidService.createBid(bid);
	}
	
	private Player getPlayerByName(String email) {
		Collection<Player> players =  playerService.getAllPlayers();
		for (Player player : players) {
			if (email.equals(player.getEmail()))  {
				return player;
			}
    }
		return null;
	}
	
}
