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

import com.kumasi.mynfl.domain.Draft;
import com.kumasi.mynfl.domain.jpa.DraftEntity;
import java.util.List;
import com.kumasi.mynfl.business.service.mapping.DraftServiceMapper;
import com.kumasi.mynfl.persistence.services.jpa.DraftPersistenceJPA;
import com.kumasi.mynfl.test.DraftFactoryForTest;
import com.kumasi.mynfl.test.DraftEntityFactoryForTest;
import com.kumasi.mynfl.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of DraftService
 */
@RunWith(MockitoJUnitRunner.class)
public class DraftServiceImplTest {

	@InjectMocks
	private DraftServiceImpl draftService;
	@Mock
	private DraftPersistenceJPA draftPersistenceJPA;
	@Mock
	private DraftServiceMapper draftServiceMapper;
	
	private DraftFactoryForTest draftFactoryForTest = new DraftFactoryForTest();

	private DraftEntityFactoryForTest draftEntityFactoryForTest = new DraftEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		DraftEntity draftEntity = draftPersistenceJPA.load(id);
		
		Draft draft = draftFactoryForTest.newDraft();
		when(draftServiceMapper.mapDraftEntityToDraft(draftEntity)).thenReturn(draft);

		// When
		Draft draftFound = draftService.findById(id);

		// Then
		assertEquals(draft.getId(),draftFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<DraftEntity> draftEntitys = new ArrayList<DraftEntity>();
		DraftEntity draftEntity1 = draftEntityFactoryForTest.newDraftEntity();
		draftEntitys.add(draftEntity1);
		DraftEntity draftEntity2 = draftEntityFactoryForTest.newDraftEntity();
		draftEntitys.add(draftEntity2);
		when(draftPersistenceJPA.loadAll()).thenReturn(draftEntitys);
		
		Draft draft1 = draftFactoryForTest.newDraft();
		when(draftServiceMapper.mapDraftEntityToDraft(draftEntity1)).thenReturn(draft1);
		Draft draft2 = draftFactoryForTest.newDraft();
		when(draftServiceMapper.mapDraftEntityToDraft(draftEntity2)).thenReturn(draft2);

		// When
		List<Draft> draftsFounds = draftService.findAll();

		// Then
		assertTrue(draft1 == draftsFounds.get(0));
		assertTrue(draft2 == draftsFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Draft draft = draftFactoryForTest.newDraft();

		DraftEntity draftEntity = draftEntityFactoryForTest.newDraftEntity();
		when(draftPersistenceJPA.load(draft.getId())).thenReturn(null);
		
		draftEntity = new DraftEntity();
		draftServiceMapper.mapDraftToDraftEntity(draft, draftEntity);
		DraftEntity draftEntitySaved = draftPersistenceJPA.save(draftEntity);
		
		Draft draftSaved = draftFactoryForTest.newDraft();
		when(draftServiceMapper.mapDraftEntityToDraft(draftEntitySaved)).thenReturn(draftSaved);

		// When
		Draft draftResult = draftService.create(draft);

		// Then
		assertTrue(draftResult == draftSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Draft draft = draftFactoryForTest.newDraft();

		DraftEntity draftEntity = draftEntityFactoryForTest.newDraftEntity();
		when(draftPersistenceJPA.load(draft.getId())).thenReturn(draftEntity);

		// When
		Exception exception = null;
		try {
			draftService.create(draft);
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
		Draft draft = draftFactoryForTest.newDraft();

		DraftEntity draftEntity = draftEntityFactoryForTest.newDraftEntity();
		when(draftPersistenceJPA.load(draft.getId())).thenReturn(draftEntity);
		
		DraftEntity draftEntitySaved = draftEntityFactoryForTest.newDraftEntity();
		when(draftPersistenceJPA.save(draftEntity)).thenReturn(draftEntitySaved);
		
		Draft draftSaved = draftFactoryForTest.newDraft();
		when(draftServiceMapper.mapDraftEntityToDraft(draftEntitySaved)).thenReturn(draftSaved);

		// When
		Draft draftResult = draftService.update(draft);

		// Then
		verify(draftServiceMapper).mapDraftToDraftEntity(draft, draftEntity);
		assertTrue(draftResult == draftSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		draftService.delete(id);

		// Then
		verify(draftPersistenceJPA).delete(id);
		
	}

}