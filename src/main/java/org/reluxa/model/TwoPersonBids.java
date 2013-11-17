package org.reluxa.model;

import lombok.Data;

public @Data
class TwoPersonBids extends Bid {

	private Player user1;

	private Player user2;

}
