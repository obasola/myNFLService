package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.TeamStat;

public class TeamStatFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public TeamStat newTeamStat() {

		Integer id = mockValues.nextInteger();

		TeamStat teamStat = new TeamStat();
		teamStat.setId(id);
		return teamStat;
	}
	
}
