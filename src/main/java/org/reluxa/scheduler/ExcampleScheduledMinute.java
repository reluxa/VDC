package org.reluxa.scheduler;

import java.util.Date;

import javax.inject.Inject;

import org.reluxa.player.service.PlayerServiceIF;

public class ExcampleScheduledMinute implements Scheduled {

  @Inject
  PlayerServiceIF playerService;
  
  @Override
  public void run() {
    try {
    System.out.println(playerService.getAllPlayers().size());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @Override
  public Date getFirstRun() {
    return new Date(System.currentTimeMillis()+3);
  }

  @Override
  public long getPeriodTimeInSeconds() {
    return 5;
  }

}
