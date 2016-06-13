package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.Status;

public class StatusFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Status newStatus() {

		Integer id = mockValues.nextInteger();

		Status status = new Status();
		status.setId(id);
		return status;
	}
	
}
