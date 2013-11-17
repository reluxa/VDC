package org.reluxa.model;

import java.util.Date;

import lombok.Data;

@Data
public abstract class Bid {

	public static enum BidStatus {
		PENDING, LOST, WON;
	}

	private BidStatus status;

	private Date creationTime;
}
