package org.reluxa.scheduler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Scheduled implements Runnable {
  
  public static final long SECONDS_IN_MINUTE = 60;
  public static final long MINUTES_IN_HOUR = 60;
  public static final long HOURS_IN_DAY = 24;
  public static final long DAYS_IN_WEEK = 7;
  
  public abstract Date getFirstRun();
  
  public abstract long getPeriodTimeInSeconds();
  
  protected Logger log = LoggerFactory.getLogger(this.getClass());
  
}
