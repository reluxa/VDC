package org.reluxa.bid.service;

import java.util.Date;

import javax.inject.Inject;

import org.reluxa.scheduler.Scheduled;
import org.reluxa.time.TimeServiceIF;

/**
 * Scheduler job running the bid evaluator.
 * @author reluxa
 */
public class BidEvaluatorScheduler extends Scheduled {
  
  @Inject
  TimeServiceIF timeService;
  
  @Inject 
  BidEvaluator bidEvaluator;
  
  @Override
  public void run() {
  	bidEvaluator.runWeeklyEvaluation();
  }

  @Override
  public Date getFirstRun() {
    return timeService.getNextJobScheduleDate();
  }

  @Override
  public long getPeriodTimeInSeconds() {
    return SECONDS_IN_MINUTE*MINUTES_IN_HOUR*HOURS_IN_DAY*DAYS_IN_WEEK;
  }

}
