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

import com.kumasi.mynfl.domain.Status;
import com.kumasi.mynfl.domain.jpa.StatusEntity;
import java.util.List;
import com.kumasi.mynfl.business.service.mapping.StatusServiceMapper;
import com.kumasi.mynfl.persistence.services.jpa.StatusPersistenceJPA;
import com.kumasi.mynfl.test.StatusFactoryForTest;
import com.kumasi.mynfl.test.StatusEntityFactoryForTest;
import com.kumasi.mynfl.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of StatusService
 */
@RunWith(MockitoJUnitRunner.class)
public class StatusServiceImplTest {

	@InjectMocks
	private StatusServiceImpl statusService;
	@Mock
	private StatusPersistenceJPA statusPersistenceJPA;
	@Mock
	private StatusServiceMapper statusServiceMapper;
	
	private StatusFactoryForTest statusFactoryForTest = new StatusFactoryForTest();

	private StatusEntityFactoryForTest statusEntityFactoryForTest = new StatusEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		StatusEntity statusEntity = statusPersistenceJPA.load(id);
		
		Status status = statusFactoryForTest.newStatus();
		when(statusServiceMapper.mapStatusEntityToStatus(statusEntity)).thenReturn(status);

		// When
		Status statusFound = statusService.findById(id);

		// Then
		assertEquals(status.getId(),statusFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<StatusEntity> statusEntitys = new ArrayList<StatusEntity>();
		StatusEntity statusEntity1 = statusEntityFactoryForTest.newStatusEntity();
		statusEntitys.add(statusEntity1);
		StatusEntity statusEntity2 = statusEntityFactoryForTest.newStatusEntity();
		statusEntitys.add(statusEntity2);
		when(statusPersistenceJPA.loadAll()).thenReturn(statusEntitys);
		
		Status status1 = statusFactoryForTest.newStatus();
		when(statusServiceMapper.mapStatusEntityToStatus(statusEntity1)).thenReturn(status1);
		Status status2 = statusFactoryForTest.newStatus();
		when(statusServiceMapper.mapStatusEntityToStatus(statusEntity2)).thenReturn(status2);

		// When
		List<Status> statussFounds = statusService.findAll();

		// Then
		assertTrue(status1 == statussFounds.get(0));
		assertTrue(status2 == statussFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Status status = statusFactoryForTest.newStatus();

		StatusEntity statusEntity = statusEntityFactoryForTest.newStatusEntity();
		when(statusPersistenceJPA.load(status.getId())).thenReturn(null);
		
		statusEntity = new StatusEntity();
		statusServiceMapper.mapStatusToStatusEntity(status, statusEntity);
		StatusEntity statusEntitySaved = statusPersistenceJPA.save(statusEntity);
		
		Status statusSaved = statusFactoryForTest.newStatus();
		when(statusServiceMapper.mapStatusEntityToStatus(statusEntitySaved)).thenReturn(statusSaved);

		// When
		Status statusResult = statusService.create(status);

		// Then
		assertTrue(statusResult == statusSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Status status = statusFactoryForTest.newStatus();

		StatusEntity statusEntity = statusEntityFactoryForTest.newStatusEntity();
		when(statusPersistenceJPA.load(status.getId())).thenReturn(statusEntity);

		// When
		Exception exception = null;
		try {
			statusService.create(status);
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
		Status status = statusFactoryForTest.newStatus();

		StatusEntity statusEntity = statusEntityFactoryForTest.newStatusEntity();
		when(statusPersistenceJPA.load(status.getId())).thenReturn(statusEntity);
		
		StatusEntity statusEntitySaved = statusEntityFactoryForTest.newStatusEntity();
		when(statusPersistenceJPA.save(statusEntity)).thenReturn(statusEntitySaved);
		
		Status statusSaved = statusFactoryForTest.newStatus();
		when(statusServiceMapper.mapStatusEntityToStatus(statusEntitySaved)).thenReturn(statusSaved);

		// When
		Status statusResult = statusService.update(status);

		// Then
		verify(statusServiceMapper).mapStatusToStatusEntity(status, statusEntity);
		assertTrue(statusResult == statusSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		statusService.delete(id);

		// Then
		verify(statusPersistenceJPA).delete(id);
		
	}

}
