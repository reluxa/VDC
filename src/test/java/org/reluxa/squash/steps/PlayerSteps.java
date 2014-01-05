package org.reluxa.squash.steps;

import org.jbehave.core.annotations.Given;
import org.reluxa.player.Player;
import org.reluxa.player.service.DuplicateUserException;
import org.reluxa.player.service.PlayerServiceIF;

public class PlayerSteps {
	
	PlayerServiceIF playerService;
	
	public PlayerSteps(PlayerServiceIF playerService) {
		this.playerService = playerService;
	}
	
	@Given("a user $email")
	public void createPlayer(String email) throws DuplicateUserException {
		Player player = new Player();
		player.setEmail(email);
		playerService.createUser(player);
	}
	
	
}
