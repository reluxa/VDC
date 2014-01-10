package org.reluxa.time;

import java.util.Date;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
public class TimeService implements TimeServiceIF {

	@Override
	public Date getCurrentTime() {
		return new Date();
	}
	
	
	@Override
	public Date getIntervalBeginDate() {
		LocalDate currentLocal = new LocalDate(getCurrentTime());
		LocalDate thisSunday = currentLocal.withDayOfWeek(DateTimeConstants.SUNDAY);
		LocalDate forWeeksBefore = thisSunday.minusWeeks(4);
		return forWeeksBefore.toDate();
	}
	
	@Override
	public Date getWeekBegin() {
		LocalDate currentLocal = new LocalDate(getCurrentTime());
		return currentLocal.withDayOfWeek(DateTimeConstants.MONDAY).toDate();
	}
	
	@Override
	public Date getWeekEnd() {
		LocalDate currentLocal = new LocalDate(getCurrentTime());
		return currentLocal.withDayOfWeek(DateTimeConstants.SUNDAY).toDate();
	}
	
	@Override
	public int getCurrentWeekNumber() {
		LocalDate currentLocal = new LocalDate(getCurrentTime());
		return currentLocal.weekOfWeekyear().get();
	}



}
