package org.reluxa.time;

import java.util.Date;

import javax.inject.Inject;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.reluxa.settings.service.SettingsServiceIF;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
public class TimeService implements TimeServiceIF {

	@Inject
	SettingsServiceIF settings;
	
	@Override
	public Date getCurrentTime() {
		if (settings.getConfig().isFixedTime()) {
			return settings.getConfig().getCurrentTime();
		}
		return new Date();
	}

	@Override
	public Date getIntervalBeginDate() {
		return getIntervalBeginDate(getCurrentTime());
	}

	@Override
	public Date getIntervalBeginDate(Date ref) {
		LocalDate currentLocal = new LocalDate(ref);
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
		LocalDateTime currentLocal = new LocalDateTime(getWeekBegin());
		currentLocal = currentLocal.plusWeeks(1).minusSeconds(1);
		return currentLocal.toDate();
	}

	@Override
	public Date getWeekBegin(Date ref) {
		LocalDate currentLocal = new LocalDate(ref);
		return currentLocal.withDayOfWeek(DateTimeConstants.MONDAY).toDate();
	}
	
	@Override
	public Date getNextJobScheduleDate() {
		LocalDate currentLocal = new LocalDate(getCurrentTime());
		return currentLocal.withDayOfWeek(DateTimeConstants.MONDAY).plusDays(7).toDate();
	}

	@Override
	public Date getWeekEnd(Date ref) {
		LocalDateTime currentLocal = new LocalDateTime(getWeekBegin(ref));
		currentLocal = currentLocal.plusWeeks(1).minusSeconds(1);
		return currentLocal.toDate();
	}

	@Override
	public int getCurrentWeekNumber() {
		LocalDate currentLocal = new LocalDate(getCurrentTime());
		return currentLocal.weekOfWeekyear().get();
	}

}
