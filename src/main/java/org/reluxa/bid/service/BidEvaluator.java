package org.reluxa.bid.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Inject;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.reluxa.bid.Bid;
import org.reluxa.bid.BidStatus;
import org.reluxa.player.Player;
import org.reluxa.settings.Config;
import org.reluxa.settings.service.SettingsServiceIF;
import org.reluxa.time.TimeServiceIF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;

public class BidEvaluator {
	
	@Inject @Getter @Setter
	private BidServiceIF bidService;
	
	@Inject	@Getter	@Setter
	private TimeServiceIF timeService;
	
	@Inject 
	private SettingsServiceIF settings;

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static Comparator<Bid> SCORE_COMPARATOR = new Comparator<Bid>() {
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
	};

	/**
	 * @return Collection<Bid> which contains the scores associated for the bid. 
	 */
	public Collection<Bid> evaluteBidsForCurrentWeek(Date intervalBegin) {
		ArrayList<Bid> allBids = new ArrayList<>(bidService.getAllBids(intervalBegin));
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
	
	public Collection<Bid> evaluteBidsForCurrentWeek() {
		return evaluteBidsForCurrentWeek(getIntervalBeginDateForNow());
	}

	
	
	/**
	 * Weekly running job, which is called automatically; 
	 */
	public void runWeeklyEvaluation() {
		//find the firstDate;
		Date firstBidCreationTime = getFirstBidCreationTime();
		if (firstBidCreationTime == null) {
			log.info("There is no bid this week...");
			return;
		}
		
		final Date periodStart = getIntervalBeginDateForReferenceDate(firstBidCreationTime);
		final Date weekStart = timeService.getWeekBegin(firstBidCreationTime);

		
		//do the eval
		Collection<Bid> evaluatedBids = evaluteBidsForCurrentWeek(periodStart);

		//get the current week
		final Date weekEnd = timeService.getWeekEnd(firstBidCreationTime);
		Collection<Bid> thisWeekBids = Collections2.filter(evaluatedBids, new Predicate<Bid>(){
			@Override
      public boolean apply(@Nullable Bid bid) {
				return weekStart.before(bid.getCreationTime()) && weekEnd.after(bid.getCreationTime());
      }
		});
		List<Bid> sorted = new ArrayList<>(thisWeekBids);
		Collections.sort(sorted, SCORE_COMPARATOR);

		Config config = settings.getConfig();
		
		//set the status and ticket id.
		for (int i=0;i<sorted.size();i++) {
			Bid bid = sorted.get(i);
			if (i < config.getNumberOfEventsPerWeek() && isPending(bid)) {
				bid.setStatus(BidStatus.WON.toString());
				bid.setTicketCode(generateTicketCode());
			} else if (isPending(bid) || isWaitingForApproval(bid)) {
				sorted.get(i).setStatus(BidStatus.LOST.toString());
			}
		}
		//save to the database
		bidService.updateAll(sorted);
	}
	
	private Date getFirstBidCreationTime() {
	  Collection<Bid> bids = getBidService().getAllNotEvaluatedBids();
	  if (bids.size() > 0) {
			Ordering<Bid> ob = new Ordering<Bid>() {
				@Override
	      public int compare(@Nullable Bid left, @Nullable Bid right) {
					return left.getCreationTime().compareTo(right.getCreationTime());
	      }
			};
			Bid firstBid = ob.min(bids);
		  return firstBid.getCreationTime();
	  }
	  return null;
  }

	private void updateScores(Bid bid, Map<Player, Integer> scoreTable) {
		Player creator = bid.getCreator();
		Player partner = bid.getPartner();
		if (partner != null && !isWaitingForApproval(bid)) {
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

	public Date getIntervalBeginDateForNow() {
		return getIntervalBeginDateForReferenceDate(timeService.getCurrentTime());
	}
	
	
	public Date getIntervalBeginDateForReferenceDate(Date ref) {
		LocalDate currentLocal = new LocalDate(ref.getTime());
		LocalDate thisSunday = currentLocal.withDayOfWeek(DateTimeConstants.SUNDAY);
		LocalDate forWeeksBefore = thisSunday.minusWeeks(4);
		return forWeeksBefore.toDate();
	}
	
	private boolean isPending(Bid bid) {
		return BidStatus.PENDING.toString().equals(bid.getStatus());
	}
	
	private boolean isWaitingForApproval(Bid bid) {
		return BidStatus.WAITING_FOR_APPOVAL.toString().equals(bid.getStatus());
	}
	
	private String generateTicketCode() {
		return RandomStringUtils.randomAlphabetic(2).toUpperCase()+RandomStringUtils.randomNumeric(4);
	}


}
