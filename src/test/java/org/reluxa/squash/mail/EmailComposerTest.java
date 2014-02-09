package org.reluxa.squash.mail;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.reluxa.bid.Bid;
import org.reluxa.bid.BidStatus;
import org.reluxa.mail.EmailComposer;
import org.reluxa.player.Player;
import org.reluxa.settings.Config;
import org.reluxa.settings.service.SettingsService;


public class EmailComposerTest {
	
	@Test
	public void testWeeklyGeneration() {
		Config config = SettingsService.DEFAULT;
		EmailComposer composer = new EmailComposer();
		composer.setConfig(config);
		
		Player player = new Player();
		player.setFullName("Full Name");
		
		Bid bid = new Bid();
		bid.setStatus(BidStatus.WON.toString());
		bid.setScore(2d);
		bid.setCreator(player);
		bid.setTicketCode("TG2167");
		
		List<Bid> bids = Arrays.asList(bid);
		
		String result = composer.getWeeklyEvalEmail(bids, player);
		assertTrue(result.indexOf("$") < 0);
	}


}
