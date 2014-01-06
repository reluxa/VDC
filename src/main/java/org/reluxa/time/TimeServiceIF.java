package org.reluxa.time;

import java.util.Date;

public interface TimeServiceIF {

	public Date getCurrentTime();

	public abstract int getCurrentWeekNumber();

	public abstract Date getWeekEnd();

	public abstract Date getWeekBegin();

	public abstract Date getIntervalBeginDate();
	
}
