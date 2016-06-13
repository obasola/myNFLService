package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.DraftRound;

public class DraftRoundFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DraftRound newDraftRound() {

		Integer id = mockValues.nextInteger();

		DraftRound draftRound = new DraftRound();
		draftRound.setId(id);
		return draftRound;
	}
	
}
