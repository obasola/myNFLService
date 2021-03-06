/*
 * Created on 5 Jun 2016 ( Time 14:55:37 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.kumasi.mynfl.domain.Schedule;
import com.kumasi.mynfl.domain.jpa.ScheduleEntity;
import com.kumasi.mynfl.business.service.mapping.ScheduleServiceMapper;
import com.kumasi.mynfl.persistence.services.jpa.SchedulePersistenceJPA;
import com.kumasi.mynfl.test.ScheduleFactoryForTest;
import com.kumasi.mynfl.test.ScheduleEntityFactoryForTest;
import com.kumasi.mynfl.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of ScheduleService
 */
@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceImplTest {

	@InjectMocks
	private ScheduleServiceImpl scheduleService;
	@Mock
	private SchedulePersistenceJPA schedulePersistenceJPA;
	@Mock
	private ScheduleServiceMapper scheduleServiceMapper;
	
	private ScheduleFactoryForTest scheduleFactoryForTest = new ScheduleFactoryForTest();

	private ScheduleEntityFactoryForTest scheduleEntityFactoryForTest = new ScheduleEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		ScheduleEntity scheduleEntity = schedulePersistenceJPA.load(id);
		
		Schedule schedule = scheduleFactoryForTest.newSchedule();
		when(scheduleServiceMapper.mapScheduleEntityToSchedule(scheduleEntity)).thenReturn(schedule);

		// When
		Schedule scheduleFound = scheduleService.findById(id);

		// Then
		assertEquals(schedule.getId(),scheduleFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<ScheduleEntity> scheduleEntitys = new ArrayList<ScheduleEntity>();
		ScheduleEntity scheduleEntity1 = scheduleEntityFactoryForTest.newScheduleEntity();
		scheduleEntitys.add(scheduleEntity1);
		ScheduleEntity scheduleEntity2 = scheduleEntityFactoryForTest.newScheduleEntity();
		scheduleEntitys.add(scheduleEntity2);
		when(schedulePersistenceJPA.loadAll()).thenReturn(scheduleEntitys);
		
		Schedule schedule1 = scheduleFactoryForTest.newSchedule();
		when(scheduleServiceMapper.mapScheduleEntityToSchedule(scheduleEntity1)).thenReturn(schedule1);
		Schedule schedule2 = scheduleFactoryForTest.newSchedule();
		when(scheduleServiceMapper.mapScheduleEntityToSchedule(scheduleEntity2)).thenReturn(schedule2);

		// When
		List<Schedule> schedulesFounds = scheduleService.findAll();

		// Then
		assertTrue(schedule1 == schedulesFounds.get(0));
		assertTrue(schedule2 == schedulesFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Schedule schedule = scheduleFactoryForTest.newSchedule();

		ScheduleEntity scheduleEntity = scheduleEntityFactoryForTest.newScheduleEntity();
		when(schedulePersistenceJPA.load(schedule.getId())).thenReturn(null);
		
		scheduleEntity = new ScheduleEntity();
		scheduleServiceMapper.mapScheduleToScheduleEntity(schedule, scheduleEntity);
		ScheduleEntity scheduleEntitySaved = schedulePersistenceJPA.save(scheduleEntity);
		
		Schedule scheduleSaved = scheduleFactoryForTest.newSchedule();
		when(scheduleServiceMapper.mapScheduleEntityToSchedule(scheduleEntitySaved)).thenReturn(scheduleSaved);

		// When
		Schedule scheduleResult = scheduleService.create(schedule);

		// Then
		assertTrue(scheduleResult == scheduleSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Schedule schedule = scheduleFactoryForTest.newSchedule();

		ScheduleEntity scheduleEntity = scheduleEntityFactoryForTest.newScheduleEntity();
		when(schedulePersistenceJPA.load(schedule.getId())).thenReturn(scheduleEntity);

		// When
		Exception exception = null;
		try {
			scheduleService.create(schedule);
		} catch(Exception e) {
			exception = e;
		}

		// Then
		assertTrue(exception instanceof IllegalStateException);
		assertEquals("already.exists", exception.getMessage());
	}

	@Test
	public void update() {
		// Given
		Schedule schedule = scheduleFactoryForTest.newSchedule();

		ScheduleEntity scheduleEntity = scheduleEntityFactoryForTest.newScheduleEntity();
		when(schedulePersistenceJPA.load(schedule.getId())).thenReturn(scheduleEntity);
		
		ScheduleEntity scheduleEntitySaved = scheduleEntityFactoryForTest.newScheduleEntity();
		when(schedulePersistenceJPA.save(scheduleEntity)).thenReturn(scheduleEntitySaved);
		
		Schedule scheduleSaved = scheduleFactoryForTest.newSchedule();
		when(scheduleServiceMapper.mapScheduleEntityToSchedule(scheduleEntitySaved)).thenReturn(scheduleSaved);

		// When
		Schedule scheduleResult = scheduleService.update(schedule);

		// Then
		verify(scheduleServiceMapper).mapScheduleToScheduleEntity(schedule, scheduleEntity);
		assertTrue(scheduleResult == scheduleSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		scheduleService.delete(id);

		// Then
		verify(schedulePersistenceJPA).delete(id);
		
	}

}
