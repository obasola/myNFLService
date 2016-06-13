/*
 * Created on 5 Jun 2016 ( Time 14:55:37 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.mynfl.domain.PlayerStatus;
import com.kumasi.mynfl.domain.jpa.PlayerStatusEntity;
import com.kumasi.mynfl.domain.jpa.PlayerEntity;
import com.kumasi.mynfl.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class PlayerStatusServiceMapperTest {

	private PlayerStatusServiceMapper playerStatusServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		playerStatusServiceMapper = new PlayerStatusServiceMapper();
		playerStatusServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'PlayerStatusEntity' to 'PlayerStatus'
	 * @param playerStatusEntity
	 */
	@Test
	public void testMapPlayerStatusEntityToPlayerStatus() {
		// Given
		PlayerStatusEntity playerStatusEntity = new PlayerStatusEntity();
		playerStatusEntity.setCode(mockValues.nextString(45));
		playerStatusEntity.setName(mockValues.nextString(45));
		playerStatusEntity.setDescription(mockValues.nextString(2000));
		playerStatusEntity.setPlayer(new PlayerEntity());
		playerStatusEntity.getPlayer().setId(mockValues.nextInteger());
		
		// When
		PlayerStatus playerStatus = playerStatusServiceMapper.mapPlayerStatusEntityToPlayerStatus(playerStatusEntity);
		
		// Then
		assertEquals(playerStatusEntity.getCode(), playerStatus.getCode());
		assertEquals(playerStatusEntity.getName(), playerStatus.getName());
		assertEquals(playerStatusEntity.getDescription(), playerStatus.getDescription());
		assertEquals(playerStatusEntity.getPlayer().getId(), playerStatus.getPlayerId());
	}
	
	/**
	 * Test : Mapping from 'PlayerStatus' to 'PlayerStatusEntity'
	 */
	@Test
	public void testMapPlayerStatusToPlayerStatusEntity() {
		// Given
		PlayerStatus playerStatus = new PlayerStatus();
		playerStatus.setCode(mockValues.nextString(45));
		playerStatus.setName(mockValues.nextString(45));
		playerStatus.setDescription(mockValues.nextString(2000));
		playerStatus.setPlayerId(mockValues.nextInteger());

		PlayerStatusEntity playerStatusEntity = new PlayerStatusEntity();
		
		// When
		playerStatusServiceMapper.mapPlayerStatusToPlayerStatusEntity(playerStatus, playerStatusEntity);
		
		// Then
		assertEquals(playerStatus.getCode(), playerStatusEntity.getCode());
		assertEquals(playerStatus.getName(), playerStatusEntity.getName());
		assertEquals(playerStatus.getDescription(), playerStatusEntity.getDescription());
		assertEquals(playerStatus.getPlayerId(), playerStatusEntity.getPlayer().getId());
	}

}