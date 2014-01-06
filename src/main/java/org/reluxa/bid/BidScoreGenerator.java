package org.reluxa.bid;

import java.util.Collection;

import javax.inject.Inject;

import org.reluxa.vaadin.widget.AbstractColumnGenerator;

public class BidScoreGenerator extends AbstractColumnGenerator<Bid, Double> {

  @Inject
  BidEvaluator bidEvaluator;
  
  @Override 
  public Double generateCell(Bid current) {
    Double result = null;
    Collection<Bid> bids = bidEvaluator.evaluteBidsForCurrentWeek();
    for (Bid bid : bids) {
      if (bid.getId() == current.getId()) {
	result = bid.getScore();
      }
    }
    return result;
  }

}
