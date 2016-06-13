package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.DraftEntity;

public class DraftEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DraftEntity newDraftEntity() {

		Integer id = mockValues.nextInteger();

		DraftEntity draftEntity = new DraftEntity();
		draftEntity.setId(id);
		return draftEntity;
	}
	
}
