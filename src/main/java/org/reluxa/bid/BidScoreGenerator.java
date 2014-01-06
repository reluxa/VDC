package org.reluxa.bid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.annotation.Nullable;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.reluxa.time.TimeServiceIF;
import org.reluxa.vaadin.widget.AbstractColumnGenerator;
import org.reluxa.vaadin.widget.Icon;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class BidScoreGenerator extends AbstractColumnGenerator<Bid, Component> {

	@Inject
	BidEvaluator bidEvaluator;
	
	@Inject
	TimeServiceIF timeService;

	@Override 
  public Component generateCell(Bid current) {
    Collection<Bid> bids = bidEvaluator.evaluteBidsForCurrentWeek();
    Collection<Bid> winners = getWinners(bids);
    for (Bid bid : bids) {
      if (bid.getId() == current.getId() && bid.getScore() != null) {
      	return new Label(getWinnerThrophy(bid, winners)+Double.toString(bid.getScore()),ContentMode.HTML);
      }
    }
    return null;
  }
	
	
	private String getWinnerThrophy(Bid bid, Collection<Bid> winners) {
		if (winners.contains(bid)) {
			return Icon.get("trophy","green").toString();
		}
		return StringUtils.EMPTY;
	}
	
	
	private Collection<Bid> getWinners(Collection<Bid> all) {

		all = Collections2.filter(all, new Predicate<Bid>(){
			@Override
      public boolean apply(@Nullable Bid bid) {
				return timeService.getWeekBegin().before(bid.getCreationTime());
      }
		});
		
		ArrayList<Bid> sorted = new ArrayList<>(all);
		
		Collections.sort(sorted, new Comparator<Bid>(){
			@Override
      public int compare(Bid o1, Bid o2) {
				if (o1.getScore() == null) {
					return 1;
				} else if (o2.getScore() == null) {
					return -1;
				} else {
					return o1.getScore().compareTo(o2.getScore());
				}
				
      }
		});
		if (sorted.size() <  bidEvaluator.getGetMaxEventsPerWeek()) {
			return sorted;
		}
		return sorted.subList(0, bidEvaluator.getGetMaxEventsPerWeek());
	}
}
