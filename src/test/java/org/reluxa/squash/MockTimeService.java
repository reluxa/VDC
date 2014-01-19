package org.reluxa.squash;

import java.util.Date;

import lombok.Data;

import org.reluxa.time.TimeServiceIF;

@Data
public class MockTimeService implements TimeServiceIF {

	Date currentTime;

	@Override
	public int getCurrentWeekNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date getWeekEnd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getWeekBegin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getIntervalBeginDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
  public Date getWeekEnd(Date ref) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Date getWeekBegin(Date ref) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Date getIntervalBeginDate(Date ref) {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Date getNextJobScheduleDate() {
	  // TODO Auto-generated method stub
	  return null;
  }
	
}
