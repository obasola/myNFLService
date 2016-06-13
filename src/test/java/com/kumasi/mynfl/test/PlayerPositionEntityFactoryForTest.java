package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.PlayerPositionEntity;

public class PlayerPositionEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PlayerPositionEntity newPlayerPositionEntity() {

		Integer id = mockValues.nextInteger();

		PlayerPositionEntity playerPositionEntity = new PlayerPositionEntity();
		playerPositionEntity.setId(id);
		return playerPositionEntity;
	}
	
}
