package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.DraftType;

public class DraftTypeFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DraftType newDraftType() {

		Integer id = mockValues.nextInteger();

		DraftType draftType = new DraftType();
		draftType.setId(id);
		return draftType;
	}
	
}
