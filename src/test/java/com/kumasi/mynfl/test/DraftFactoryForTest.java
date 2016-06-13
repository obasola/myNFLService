package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.Draft;

public class DraftFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Draft newDraft() {

		Integer id = mockValues.nextInteger();

		Draft draft = new Draft();
		draft.setId(id);
		return draft;
	}
	
}
