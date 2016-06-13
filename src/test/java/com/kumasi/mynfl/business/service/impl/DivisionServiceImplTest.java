/*
 * Created on 5 Jun 2016 ( Time 14:55:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.kumasi.mynfl.domain.Division;
import com.kumasi.mynfl.domain.jpa.DivisionEntity;
import java.util.List;
import com.kumasi.mynfl.business.service.mapping.DivisionServiceMapper;
import com.kumasi.mynfl.persistence.services.jpa.DivisionPersistenceJPA;
import com.kumasi.mynfl.test.DivisionFactoryForTest;
import com.kumasi.mynfl.test.DivisionEntityFactoryForTest;
import com.kumasi.mynfl.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of DivisionService
 */
@RunWith(MockitoJUnitRunner.class)
public class DivisionServiceImplTest {

	@InjectMocks
	private DivisionServiceImpl divisionService;
	@Mock
	private DivisionPersistenceJPA divisionPersistenceJPA;
	@Mock
	private DivisionServiceMapper divisionServiceMapper;
	
	private DivisionFactoryForTest divisionFactoryForTest = new DivisionFactoryForTest();

	private DivisionEntityFactoryForTest divisionEntityFactoryForTest = new DivisionEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		DivisionEntity divisionEntity = divisionPersistenceJPA.load(id);
		
		Division division = divisionFactoryForTest.newDivision();
		when(divisionServiceMapper.mapDivisionEntityToDivision(divisionEntity)).thenReturn(division);

		// When
		Division divisionFound = divisionService.findById(id);

		// Then
		assertEquals(division.getId(),divisionFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<DivisionEntity> divisionEntitys = new ArrayList<DivisionEntity>();
		DivisionEntity divisionEntity1 = divisionEntityFactoryForTest.newDivisionEntity();
		divisionEntitys.add(divisionEntity1);
		DivisionEntity divisionEntity2 = divisionEntityFactoryForTest.newDivisionEntity();
		divisionEntitys.add(divisionEntity2);
		when(divisionPersistenceJPA.loadAll()).thenReturn(divisionEntitys);
		
		Division division1 = divisionFactoryForTest.newDivision();
		when(divisionServiceMapper.mapDivisionEntityToDivision(divisionEntity1)).thenReturn(division1);
		Division division2 = divisionFactoryForTest.newDivision();
		when(divisionServiceMapper.mapDivisionEntityToDivision(divisionEntity2)).thenReturn(division2);

		// When
		List<Division> divisionsFounds = divisionService.findAll();

		// Then
		assertTrue(division1 == divisionsFounds.get(0));
		assertTrue(division2 == divisionsFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Division division = divisionFactoryForTest.newDivision();

		DivisionEntity divisionEntity = divisionEntityFactoryForTest.newDivisionEntity();
		when(divisionPersistenceJPA.load(division.getId())).thenReturn(null);
		
		divisionEntity = new DivisionEntity();
		divisionServiceMapper.mapDivisionToDivisionEntity(division, divisionEntity);
		DivisionEntity divisionEntitySaved = divisionPersistenceJPA.save(divisionEntity);
		
		Division divisionSaved = divisionFactoryForTest.newDivision();
		when(divisionServiceMapper.mapDivisionEntityToDivision(divisionEntitySaved)).thenReturn(divisionSaved);

		// When
		Division divisionResult = divisionService.create(division);

		// Then
		assertTrue(divisionResult == divisionSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Division division = divisionFactoryForTest.newDivision();

		DivisionEntity divisionEntity = divisionEntityFactoryForTest.newDivisionEntity();
		when(divisionPersistenceJPA.load(division.getId())).thenReturn(divisionEntity);

		// When
		Exception exception = null;
		try {
			divisionService.create(division);
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
		Division division = divisionFactoryForTest.newDivision();

		DivisionEntity divisionEntity = divisionEntityFactoryForTest.newDivisionEntity();
		when(divisionPersistenceJPA.load(division.getId())).thenReturn(divisionEntity);
		
		DivisionEntity divisionEntitySaved = divisionEntityFactoryForTest.newDivisionEntity();
		when(divisionPersistenceJPA.save(divisionEntity)).thenReturn(divisionEntitySaved);
		
		Division divisionSaved = divisionFactoryForTest.newDivision();
		when(divisionServiceMapper.mapDivisionEntityToDivision(divisionEntitySaved)).thenReturn(divisionSaved);

		// When
		Division divisionResult = divisionService.update(division);

		// Then
		verify(divisionServiceMapper).mapDivisionToDivisionEntity(division, divisionEntity);
		assertTrue(divisionResult == divisionSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		divisionService.delete(id);

		// Then
		verify(divisionPersistenceJPA).delete(id);
		
	}

}