package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.StatusEntity;

public class StatusEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public StatusEntity newStatusEntity() {

		Integer id = mockValues.nextInteger();

		StatusEntity statusEntity = new StatusEntity();
		statusEntity.setId(id);
		return statusEntity;
	}
	
}
