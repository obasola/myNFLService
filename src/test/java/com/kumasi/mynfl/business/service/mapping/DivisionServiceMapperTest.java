/*
 * Created on 5 Jun 2016 ( Time 14:55:36 )
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
import com.kumasi.mynfl.domain.Division;
import com.kumasi.mynfl.domain.jpa.DivisionEntity;
import com.kumasi.mynfl.domain.jpa.ConferenceEntity;
import com.kumasi.mynfl.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class DivisionServiceMapperTest {

	private DivisionServiceMapper divisionServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		divisionServiceMapper = new DivisionServiceMapper();
		divisionServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'DivisionEntity' to 'Division'
	 * @param divisionEntity
	 */
	@Test
	public void testMapDivisionEntityToDivision() {
		// Given
		DivisionEntity divisionEntity = new DivisionEntity();
		divisionEntity.setName(mockValues.nextString(45));
		divisionEntity.setConference(new ConferenceEntity());
		divisionEntity.getConference().setId(mockValues.nextInteger());
		
		// When
		Division division = divisionServiceMapper.mapDivisionEntityToDivision(divisionEntity);
		
		// Then
		assertEquals(divisionEntity.getName(), division.getName());
		assertEquals(divisionEntity.getConference().getId(), division.getConferenceId());
	}
	
	/**
	 * Test : Mapping from 'Division' to 'DivisionEntity'
	 */
	@Test
	public void testMapDivisionToDivisionEntity() {
		// Given
		Division division = new Division();
		division.setName(mockValues.nextString(45));
		division.setConferenceId(mockValues.nextInteger());

		DivisionEntity divisionEntity = new DivisionEntity();
		
		// When
		divisionServiceMapper.mapDivisionToDivisionEntity(division, divisionEntity);
		
		// Then
		assertEquals(division.getName(), divisionEntity.getName());
		assertEquals(division.getConferenceId(), divisionEntity.getConference().getId());
	}

}