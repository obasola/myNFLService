package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.TeamEntity;

public class TeamEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public TeamEntity newTeamEntity() {

		Integer id = mockValues.nextInteger();

		TeamEntity teamEntity = new TeamEntity();
		teamEntity.setId(id);
		return teamEntity;
	}
	
}
