package org.reluxa.bid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.reluxa.bid.service.BidServiceIF;
import org.reluxa.player.Player;
import org.reluxa.time.TimeServiceIF;

public class BidEvaluator {

  @Inject @Getter @Setter
  private BidServiceIF bidService;

  @Inject @Getter @Setter
  private TimeServiceIF timeService;
  
  @Getter
  private int getMaxEventsPerWeek = 5;

  public Collection<Bid> evaluteBidsForCurrentWeek() {
    ArrayList<Bid> allBids = new ArrayList<>(bidService.getAllBids(getIntervalBeginDate()));
    Collections.sort(allBids, new Comparator<Bid>() {
      @Override
      public int compare(Bid o1, Bid o2) {
	return o1.getCreationTime().compareTo(o2.getCreationTime());
      }
    });

    Map<Player, Integer> scoreTable = new HashMap<>();
    for (Bid bid : allBids) {
      updateScores(bid, scoreTable);
    }
    return allBids;
  }
  
  private void updateScores(Bid bid, Map<Player, Integer> scoreTable) {
    Player creator = bid.getCreator();
    Player partner = bid.getPartner();
    if (partner != null && !BidStatus.WAITING_FOR_APPOVAL.toString().equals(bid.getStatus())) {
      double ppoints = getAndIncrement(partner, scoreTable, 1);
      double cpoints = getAndIncrement(creator, scoreTable, 1);
      bid.setScore((ppoints + cpoints) * 0.5);
    } else if (partner == null) {
      bid.setScore(new Double(getAndIncrement(creator, scoreTable, 2)));
    } else {
      bid.setScore(null);
    }
  }

  private Integer getAndIncrement(Player player, Map<Player, Integer> scoreTable, int by) {
    Integer score = scoreTable.get(player);
    if (score == null) {
      score = by;
    } else {
      score += by;
    }
    scoreTable.put(player, score);
    return score;
  }

  public Date getIntervalBeginDate() {
    LocalDate currentLocal = new LocalDate(timeService.getCurrentTime().getTime());
    LocalDate thisSunday = currentLocal.withDayOfWeek(DateTimeConstants.SUNDAY);
    LocalDate forWeeksBefore = thisSunday.minusWeeks(4);
    return forWeeksBefore.toDate();
  }

}
