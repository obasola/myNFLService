package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.Division;

public class DivisionFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Division newDivision() {

		Integer id = mockValues.nextInteger();

		Division division = new Division();
		division.setId(id);
		return division;
	}
	
}
