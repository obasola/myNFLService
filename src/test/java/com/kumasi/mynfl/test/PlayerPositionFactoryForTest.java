package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.PlayerPosition;

public class PlayerPositionFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PlayerPosition newPlayerPosition() {

		Integer id = mockValues.nextInteger();

		PlayerPosition playerPosition = new PlayerPosition();
		playerPosition.setId(id);
		return playerPosition;
	}
	
}
