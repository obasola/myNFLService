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
import com.kumasi.mynfl.domain.Schedule;
import com.kumasi.mynfl.domain.jpa.ScheduleEntity;
import com.kumasi.mynfl.domain.jpa.TeamEntity;
import com.kumasi.mynfl.domain.jpa.ScheduleTypeEntity;
import com.kumasi.mynfl.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class ScheduleServiceMapperTest {

	private ScheduleServiceMapper scheduleServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		scheduleServiceMapper = new ScheduleServiceMapper();
		scheduleServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'ScheduleEntity' to 'Schedule'
	 * @param scheduleEntity
	 */
	@Test
	public void testMapScheduleEntityToSchedule() {
		// Given
		ScheduleEntity scheduleEntity = new ScheduleEntity();
		scheduleEntity.setYear(mockValues.nextString(45));
		scheduleEntity.setGamedate(mockValues.nextString(45));
		scheduleEntity.setGamelocation(mockValues.nextString(45));
		scheduleEntity.setOpponent(mockValues.nextString(45));
		scheduleEntity.setOpponentScore(mockValues.nextInteger());
		scheduleEntity.setTeamScore(mockValues.nextInteger());
		scheduleEntity.setOutcome(mockValues.nextString(1));
		scheduleEntity.setTeam(new TeamEntity());
		scheduleEntity.getTeam().setId(mockValues.nextInteger());
		scheduleEntity.setScheduleType(new ScheduleTypeEntity());
		scheduleEntity.getScheduleType().setId(mockValues.nextInteger());
		
		// When
		Schedule schedule = scheduleServiceMapper.mapScheduleEntityToSchedule(scheduleEntity);
		
		// Then
		assertEquals(scheduleEntity.getYear(), schedule.getYear());
		assertEquals(scheduleEntity.getGamedate(), schedule.getGamedate());
		assertEquals(scheduleEntity.getGamelocation(), schedule.getGamelocation());
		assertEquals(scheduleEntity.getOpponent(), schedule.getOpponent());
		assertEquals(scheduleEntity.getOpponentScore(), schedule.getOpponentScore());
		assertEquals(scheduleEntity.getTeamScore(), schedule.getTeamScore());
		assertEquals(scheduleEntity.getOutcome(), schedule.getOutcome());
		assertEquals(scheduleEntity.getTeam().getId(), schedule.getTeamId());
		assertEquals(scheduleEntity.getScheduleType().getId(), schedule.getScheduleTypeId());
	}
	
	/**
	 * Test : Mapping from 'Schedule' to 'ScheduleEntity'
	 */
	@Test
	public void testMapScheduleToScheduleEntity() {
		// Given
		Schedule schedule = new Schedule();
		schedule.setYear(mockValues.nextString(45));
		schedule.setGamedate(mockValues.nextString(45));
		schedule.setGamelocation(mockValues.nextString(45));
		schedule.setOpponent(mockValues.nextString(45));
		schedule.setOpponentScore(mockValues.nextInteger());
		schedule.setTeamScore(mockValues.nextInteger());
		schedule.setOutcome(mockValues.nextString(1));
		schedule.setTeamId(mockValues.nextInteger());
		schedule.setScheduleTypeId(mockValues.nextInteger());

		ScheduleEntity scheduleEntity = new ScheduleEntity();
		
		// When
		scheduleServiceMapper.mapScheduleToScheduleEntity(schedule, scheduleEntity);
		
		// Then
		assertEquals(schedule.getYear(), scheduleEntity.getYear());
		assertEquals(schedule.getGamedate(), scheduleEntity.getGamedate());
		assertEquals(schedule.getGamelocation(), scheduleEntity.getGamelocation());
		assertEquals(schedule.getOpponent(), scheduleEntity.getOpponent());
		assertEquals(schedule.getOpponentScore(), scheduleEntity.getOpponentScore());
		assertEquals(schedule.getTeamScore(), scheduleEntity.getTeamScore());
		assertEquals(schedule.getOutcome(), scheduleEntity.getOutcome());
		assertEquals(schedule.getTeamId(), scheduleEntity.getTeam().getId());
		assertEquals(schedule.getScheduleTypeId(), scheduleEntity.getScheduleType().getId());
	}

}