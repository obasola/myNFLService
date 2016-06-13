package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.PlayerStatusEntity;

public class PlayerStatusEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PlayerStatusEntity newPlayerStatusEntity() {

		Integer id = mockValues.nextInteger();
		Integer statusId = mockValues.nextInteger();

		PlayerStatusEntity playerStatusEntity = new PlayerStatusEntity();
		playerStatusEntity.setId(id);
		playerStatusEntity.setStatusId(statusId);
		return playerStatusEntity;
	}
	
}
