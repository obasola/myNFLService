package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.DraftTypeEntity;

public class DraftTypeEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DraftTypeEntity newDraftTypeEntity() {

		Integer id = mockValues.nextInteger();

		DraftTypeEntity draftTypeEntity = new DraftTypeEntity();
		draftTypeEntity.setId(id);
		return draftTypeEntity;
	}
	
}
