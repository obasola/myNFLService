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

import com.kumasi.mynfl.domain.Conference;
import com.kumasi.mynfl.domain.jpa.ConferenceEntity;
import java.util.List;
import com.kumasi.mynfl.business.service.mapping.ConferenceServiceMapper;
import com.kumasi.mynfl.persistence.services.jpa.ConferencePersistenceJPA;
import com.kumasi.mynfl.test.ConferenceFactoryForTest;
import com.kumasi.mynfl.test.ConferenceEntityFactoryForTest;
import com.kumasi.mynfl.test.MockValues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Test : Implementation of ConferenceService
 */
@RunWith(MockitoJUnitRunner.class)
public class ConferenceServiceImplTest {

	@InjectMocks
	private ConferenceServiceImpl conferenceService;
	@Mock
	private ConferencePersistenceJPA conferencePersistenceJPA;
	@Mock
	private ConferenceServiceMapper conferenceServiceMapper;
	
	private ConferenceFactoryForTest conferenceFactoryForTest = new ConferenceFactoryForTest();

	private ConferenceEntityFactoryForTest conferenceEntityFactoryForTest = new ConferenceEntityFactoryForTest();

	private MockValues mockValues = new MockValues();
	
	@Test
	public void findById() {
		// Given
		Integer id = mockValues.nextInteger();
		
		ConferenceEntity conferenceEntity = conferencePersistenceJPA.load(id);
		
		Conference conference = conferenceFactoryForTest.newConference();
		when(conferenceServiceMapper.mapConferenceEntityToConference(conferenceEntity)).thenReturn(conference);

		// When
		Conference conferenceFound = conferenceService.findById(id);

		// Then
		assertEquals(conference.getId(),conferenceFound.getId());
	}

	@Test
	public void findAll() {
		// Given
		List<ConferenceEntity> conferenceEntitys = new ArrayList<ConferenceEntity>();
		ConferenceEntity conferenceEntity1 = conferenceEntityFactoryForTest.newConferenceEntity();
		conferenceEntitys.add(conferenceEntity1);
		ConferenceEntity conferenceEntity2 = conferenceEntityFactoryForTest.newConferenceEntity();
		conferenceEntitys.add(conferenceEntity2);
		when(conferencePersistenceJPA.loadAll()).thenReturn(conferenceEntitys);
		
		Conference conference1 = conferenceFactoryForTest.newConference();
		when(conferenceServiceMapper.mapConferenceEntityToConference(conferenceEntity1)).thenReturn(conference1);
		Conference conference2 = conferenceFactoryForTest.newConference();
		when(conferenceServiceMapper.mapConferenceEntityToConference(conferenceEntity2)).thenReturn(conference2);

		// When
		List<Conference> conferencesFounds = conferenceService.findAll();

		// Then
		assertTrue(conference1 == conferencesFounds.get(0));
		assertTrue(conference2 == conferencesFounds.get(1));
	}

	@Test
	public void create() {
		// Given
		Conference conference = conferenceFactoryForTest.newConference();

		ConferenceEntity conferenceEntity = conferenceEntityFactoryForTest.newConferenceEntity();
		when(conferencePersistenceJPA.load(conference.getId())).thenReturn(null);
		
		conferenceEntity = new ConferenceEntity();
		conferenceServiceMapper.mapConferenceToConferenceEntity(conference, conferenceEntity);
		ConferenceEntity conferenceEntitySaved = conferencePersistenceJPA.save(conferenceEntity);
		
		Conference conferenceSaved = conferenceFactoryForTest.newConference();
		when(conferenceServiceMapper.mapConferenceEntityToConference(conferenceEntitySaved)).thenReturn(conferenceSaved);

		// When
		Conference conferenceResult = conferenceService.create(conference);

		// Then
		assertTrue(conferenceResult == conferenceSaved);
	}

	@Test
	public void createKOExists() {
		// Given
		Conference conference = conferenceFactoryForTest.newConference();

		ConferenceEntity conferenceEntity = conferenceEntityFactoryForTest.newConferenceEntity();
		when(conferencePersistenceJPA.load(conference.getId())).thenReturn(conferenceEntity);

		// When
		Exception exception = null;
		try {
			conferenceService.create(conference);
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
		Conference conference = conferenceFactoryForTest.newConference();

		ConferenceEntity conferenceEntity = conferenceEntityFactoryForTest.newConferenceEntity();
		when(conferencePersistenceJPA.load(conference.getId())).thenReturn(conferenceEntity);
		
		ConferenceEntity conferenceEntitySaved = conferenceEntityFactoryForTest.newConferenceEntity();
		when(conferencePersistenceJPA.save(conferenceEntity)).thenReturn(conferenceEntitySaved);
		
		Conference conferenceSaved = conferenceFactoryForTest.newConference();
		when(conferenceServiceMapper.mapConferenceEntityToConference(conferenceEntitySaved)).thenReturn(conferenceSaved);

		// When
		Conference conferenceResult = conferenceService.update(conference);

		// Then
		verify(conferenceServiceMapper).mapConferenceToConferenceEntity(conference, conferenceEntity);
		assertTrue(conferenceResult == conferenceSaved);
	}

	@Test
	public void delete() {
		// Given
		Integer id = mockValues.nextInteger();

		// When
		conferenceService.delete(id);

		// Then
		verify(conferencePersistenceJPA).delete(id);
		
	}

}