package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.ScheduleType;

public class ScheduleTypeFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ScheduleType newScheduleType() {

		Integer id = mockValues.nextInteger();

		ScheduleType scheduleType = new ScheduleType();
		scheduleType.setId(id);
		return scheduleType;
	}
	
}
