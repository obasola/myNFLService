package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.DraftRoundEntity;

public class DraftRoundEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DraftRoundEntity newDraftRoundEntity() {

		Integer id = mockValues.nextInteger();

		DraftRoundEntity draftRoundEntity = new DraftRoundEntity();
		draftRoundEntity.setId(id);
		return draftRoundEntity;
	}
	
}
