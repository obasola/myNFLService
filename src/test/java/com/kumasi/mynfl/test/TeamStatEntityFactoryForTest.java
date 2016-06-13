package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.TeamStatEntity;

public class TeamStatEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public TeamStatEntity newTeamStatEntity() {

		Integer id = mockValues.nextInteger();

		TeamStatEntity teamStatEntity = new TeamStatEntity();
		teamStatEntity.setId(id);
		return teamStatEntity;
	}
	
}
