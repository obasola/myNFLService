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

import com.kumasi.mynfl.domain.ScheduleType;
import com.kumasi.mynfl.domain.jpa.ScheduleTypeEntity;
import java.util.List;
import com.kumasi.mynfl.business.service.mapping.ScheduleTypeServiceMapper;
import com.kumasi.mynfl.persistence.services.jpa.ScheduleTypePersistenceJPA;
import com.kumasi.mynfl.test.ScheduleTypeFactoryForTest;
import com.kumasi.mynfl.test.ScheduleTypeEntityFactoryForTest;
import com.kumasi.mynfl.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of ScheduleTypeService
 */
@RunWith(MockitoJUnitRunner.class)
public class ScheduleTypeServiceImplTest {

	@InjectMocks
	private ScheduleTypeServiceImpl scheduleTypeService;
	@Mock
	private ScheduleTypePersistenceJPA scheduleTypePersistenceJPA;
	@Mock
	private ScheduleTypeServiceMapper scheduleTypeServiceMapper;
	
	private ScheduleTypeFactoryForTest scheduleTypeFactoryForTest = new ScheduleTypeFactoryForTest();

	private ScheduleTypeEntityFactoryForTest scheduleTypeEntityFactoryForTest = new ScheduleTypeEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		ScheduleTypeEntity scheduleTypeEntity = scheduleTypePersistenceJPA.load(id);
		
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();
		when(scheduleTypeServiceMapper.mapScheduleTypeEntityToScheduleType(scheduleTypeEntity)).thenReturn(scheduleType);

		// When
		ScheduleType scheduleTypeFound = scheduleTypeService.findById(id);

		// Then
		assertEquals(scheduleType.getId(),scheduleTypeFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<ScheduleTypeEntity> scheduleTypeEntitys = new ArrayList<ScheduleTypeEntity>();
		ScheduleTypeEntity scheduleTypeEntity1 = scheduleTypeEntityFactoryForTest.newScheduleTypeEntity();
		scheduleTypeEntitys.add(scheduleTypeEntity1);
		ScheduleTypeEntity scheduleTypeEntity2 = scheduleTypeEntityFactoryForTest.newScheduleTypeEntity();
		scheduleTypeEntitys.add(scheduleTypeEntity2);
		when(scheduleTypePersistenceJPA.loadAll()).thenReturn(scheduleTypeEntitys);
		
		ScheduleType scheduleType1 = scheduleTypeFactoryForTest.newScheduleType();
		when(scheduleTypeServiceMapper.mapScheduleTypeEntityToScheduleType(scheduleTypeEntity1)).thenReturn(scheduleType1);
		ScheduleType scheduleType2 = scheduleTypeFactoryForTest.newScheduleType();
		when(scheduleTypeServiceMapper.mapScheduleTypeEntityToScheduleType(scheduleTypeEntity2)).thenReturn(scheduleType2);

		// When
		List<ScheduleType> scheduleTypesFounds = scheduleTypeService.findAll();

		// Then
		assertTrue(scheduleType1 == scheduleTypesFounds.get(0));
		assertTrue(scheduleType2 == scheduleTypesFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();

		ScheduleTypeEntity scheduleTypeEntity = scheduleTypeEntityFactoryForTest.newScheduleTypeEntity();
		when(scheduleTypePersistenceJPA.load(scheduleType.getId())).thenReturn(null);
		
		scheduleTypeEntity = new ScheduleTypeEntity();
		scheduleTypeServiceMapper.mapScheduleTypeToScheduleTypeEntity(scheduleType, scheduleTypeEntity);
		ScheduleTypeEntity scheduleTypeEntitySaved = scheduleTypePersistenceJPA.save(scheduleTypeEntity);
		
		ScheduleType scheduleTypeSaved = scheduleTypeFactoryForTest.newScheduleType();
		when(scheduleTypeServiceMapper.mapScheduleTypeEntityToScheduleType(scheduleTypeEntitySaved)).thenReturn(scheduleTypeSaved);

		// When
		ScheduleType scheduleTypeResult = scheduleTypeService.create(scheduleType);

		// Then
		assertTrue(scheduleTypeResult == scheduleTypeSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();

		ScheduleTypeEntity scheduleTypeEntity = scheduleTypeEntityFactoryForTest.newScheduleTypeEntity();
		when(scheduleTypePersistenceJPA.load(scheduleType.getId())).thenReturn(scheduleTypeEntity);

		// When
		Exception exception = null;
		try {
			scheduleTypeService.create(scheduleType);
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
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();

		ScheduleTypeEntity scheduleTypeEntity = scheduleTypeEntityFactoryForTest.newScheduleTypeEntity();
		when(scheduleTypePersistenceJPA.load(scheduleType.getId())).thenReturn(scheduleTypeEntity);
		
		ScheduleTypeEntity scheduleTypeEntitySaved = scheduleTypeEntityFactoryForTest.newScheduleTypeEntity();
		when(scheduleTypePersistenceJPA.save(scheduleTypeEntity)).thenReturn(scheduleTypeEntitySaved);
		
		ScheduleType scheduleTypeSaved = scheduleTypeFactoryForTest.newScheduleType();
		when(scheduleTypeServiceMapper.mapScheduleTypeEntityToScheduleType(scheduleTypeEntitySaved)).thenReturn(scheduleTypeSaved);

		// When
		ScheduleType scheduleTypeResult = scheduleTypeService.update(scheduleType);

		// Then
		verify(scheduleTypeServiceMapper).mapScheduleTypeToScheduleTypeEntity(scheduleType, scheduleTypeEntity);
		assertTrue(scheduleTypeResult == scheduleTypeSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		scheduleTypeService.delete(id);

		// Then
		verify(scheduleTypePersistenceJPA).delete(id);
		
	}

}