package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.DivisionEntity;

public class DivisionEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DivisionEntity newDivisionEntity() {

		Integer id = mockValues.nextInteger();

		DivisionEntity divisionEntity = new DivisionEntity();
		divisionEntity.setId(id);
		return divisionEntity;
	}
	
}
