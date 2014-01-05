package org.reluxa.squash.steps;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Date;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.reluxa.bid.Bid;
import org.reluxa.bid.BidEvaluator;
import org.reluxa.bid.service.BidServiceIF;
import org.reluxa.squash.MockTimeService;

public class BidEvaluatorSteps {
	
	BidServiceIF bidService;
	
	MockTimeService timerService;
	
	Collection<Bid> evaluatedBids;
	
	public BidEvaluatorSteps(BidServiceIF bidService, MockTimeService timeService) {
		this.timerService = timeService;
		this.bidService = bidService;
	}
	
	@Given("the current date is $date")
	public void setCurrentDate(Date date) {
		timerService.setCurrentTime(date);
	}
	
	@When("the system calculates the bids")
	public void calculate() {
		BidEvaluator bidEvaluator = new BidEvaluator();
		bidEvaluator.setBidService(bidService);
		bidEvaluator.setTimeService(timerService);
		evaluatedBids = bidEvaluator.evaluteBidsForCurrentWeek();
	}
	
	@Then("the bid with id $id is going to have $score points")
	public void check(long id, double score) {
		Bid bid = null;
		for (Bid b: evaluatedBids) {
	    if (b.getId() == id) {
	    	bid = b;
	    	break;
	    }
    }
		assertThat(bid.getScore(), is(score));
	}

}
