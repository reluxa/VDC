package org.reluxa.squash;

import java.util.Date;

import lombok.Data;

import org.reluxa.time.TimeServiceIF;

@Data
public class MockTimeService implements TimeServiceIF {

	Date currentTime;
	
}
