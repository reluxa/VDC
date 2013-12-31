package org.reluxa.model;

import java.util.List;

import org.reluxa.bid.Bid;

import lombok.Data;

@Data
public class Week {

	private int weekNo;

	private int year;

	private List<Bid> bids;

}
