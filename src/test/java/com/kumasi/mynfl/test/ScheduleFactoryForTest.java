package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.Schedule;

public class ScheduleFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Schedule newSchedule() {

		Integer id = mockValues.nextInteger();

		Schedule schedule = new Schedule();
		schedule.setId(id);
		return schedule;
	}
	
}
