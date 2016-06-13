package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.PlayerStatus;

public class PlayerStatusFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public PlayerStatus newPlayerStatus() {

		Integer id = mockValues.nextInteger();
		Integer statusId = mockValues.nextInteger();

		PlayerStatus playerStatus = new PlayerStatus();
		playerStatus.setId(id);
		playerStatus.setStatusId(statusId);
		return playerStatus;
	}
	
}
