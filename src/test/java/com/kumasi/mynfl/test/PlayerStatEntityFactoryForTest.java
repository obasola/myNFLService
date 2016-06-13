package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.PlayerStatEntity;

public class PlayerStatEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PlayerStatEntity newPlayerStatEntity() {

		Integer id = mockValues.nextInteger();

		PlayerStatEntity playerStatEntity = new PlayerStatEntity();
		playerStatEntity.setId(id);
		return playerStatEntity;
	}
	
}
