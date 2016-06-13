package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.DraftTeamEntity;

public class DraftTeamEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public DraftTeamEntity newDraftTeamEntity() {

		Integer id = mockValues.nextInteger();

		DraftTeamEntity draftTeamEntity = new DraftTeamEntity();
		draftTeamEntity.setId(id);
		return draftTeamEntity;
	}
	
}
