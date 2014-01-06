package org.reluxa.time;

import java.util.Date;

public class TimeService implements TimeServiceIF {

	@Override
	public Date getCurrentTime() {
		return new Date();
	}

}
