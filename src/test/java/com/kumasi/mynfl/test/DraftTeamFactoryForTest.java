package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.DraftTeam;

public class DraftTeamFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DraftTeam newDraftTeam() {

		Integer id = mockValues.nextInteger();

		DraftTeam draftTeam = new DraftTeam();
		draftTeam.setId(id);
		return draftTeam;
	}
	
}
