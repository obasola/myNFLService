package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.Team;

public class TeamFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Team newTeam() {

		Integer id = mockValues.nextInteger();

		Team team = new Team();
		team.setId(id);
		return team;
	}
	
}
