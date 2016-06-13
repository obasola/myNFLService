package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.ScheduleEntity;

public class ScheduleEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ScheduleEntity newScheduleEntity() {

		Integer id = mockValues.nextInteger();

		ScheduleEntity scheduleEntity = new ScheduleEntity();
		scheduleEntity.setId(id);
		return scheduleEntity;
	}
	
}
