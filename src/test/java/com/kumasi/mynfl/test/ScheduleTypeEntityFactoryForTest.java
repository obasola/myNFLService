package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.ScheduleTypeEntity;

public class ScheduleTypeEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ScheduleTypeEntity newScheduleTypeEntity() {

		Integer id = mockValues.nextInteger();

		ScheduleTypeEntity scheduleTypeEntity = new ScheduleTypeEntity();
		scheduleTypeEntity.setId(id);
		return scheduleTypeEntity;
	}
	
}
