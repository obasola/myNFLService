package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.PlayerStat;

public class PlayerStatFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PlayerStat newPlayerStat() {

		Integer id = mockValues.nextInteger();

		PlayerStat playerStat = new PlayerStat();
		playerStat.setId(id);
		return playerStat;
	}
	
}
