package org.reluxa.model;

import java.util.List;

import lombok.Data;

import org.reluxa.bid.Bid;

@Data
public class Week {

	private int weekNo;

	private int year;

	private List<Bid> bids;

}
