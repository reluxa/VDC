package org.reluxa.bid;

import java.util.Date;

import javax.inject.Inject;

import org.reluxa.scheduler.Scheduled;
import org.reluxa.time.TimeServiceIF;

public class BidEvaluatorScheduler implements Scheduled {
  
  @Inject
  TimeServiceIF timeService;
  
  @Inject BidEvaluator bidEvaluator;
  
  @Override
  public void run() {
    Date date = getLastRunDate();
    //bidEvaluator.evaluteBidSince(date);
  }

  /**
   * @return the sunday evening time when the last bid evaluation took place.
   */
  private Date getLastRunDate() {
    //get all the pending bids and order vy creation time
    
    //get the first one.
    
    //find the preceeding sunday
    return null;
  }

  @Override
  public Date getFirstRun() {
    return timeService.getWeekEnd();
  }

  @Override
  public long getPeriodTimeInSeconds() {
    return SECONDS_IN_MINUTE*MINUTES_IN_HOUR*HOURS_IN_DAY*DAYS_IN_WEEK;
  }

}
