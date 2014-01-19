package org.reluxa.time;

import java.util.Date;

public interface TimeServiceIF {

	Date getCurrentTime();

	int getCurrentWeekNumber();

	Date getWeekEnd();

	Date getWeekBegin();
	
	Date getWeekEnd(Date ref);

	Date getWeekBegin(Date ref);

	/**
	 * @return 4 week earlier date compared to the start of the current week;
	 */
	Date getIntervalBeginDate();

	/**
	 * @return 4 week earlier date based on the date in reference;
	 */
	Date getIntervalBeginDate(Date ref);

	Date getNextJobScheduleDate();
	
}
