package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.PlayerEntity;

public class PlayerEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PlayerEntity newPlayerEntity() {

		Integer id = mockValues.nextInteger();

		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setId(id);
		return playerEntity;
	}
	
}
