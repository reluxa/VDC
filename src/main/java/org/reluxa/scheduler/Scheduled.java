package org.reluxa.scheduler;

import java.util.Date;

public interface Scheduled extends Runnable {
  
  public static final long SECONDS_IN_MINUTE = 60;
  public static final long MINUTES_IN_HOUR = 60;
  public static final long HOURS_IN_DAY = 24;
  public static final long DAYS_IN_WEEK = 7;
  
  public Date getFirstRun();
  
  public long getPeriodTimeInSeconds();
  
}
