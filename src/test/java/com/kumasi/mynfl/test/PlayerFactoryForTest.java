package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.Player;

public class PlayerFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Player newPlayer() {

		Integer id = mockValues.nextInteger();

		Player player = new Player();
		player.setId(id);
		return player;
	}
	
}
