package com.kumasi.mynfl.web.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//--- Entities
import com.kumasi.mynfl.domain.Conference;
import com.kumasi.mynfl.test.ConferenceFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.ConferenceService;


import com.kumasi.mynfl.web.common.Message;
import com.kumasi.mynfl.web.common.MessageHelper;
import com.kumasi.mynfl.web.common.MessageType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceControllerTest {
	
	@InjectMocks
	private ConferenceController conferenceController;
	@Mock
	private ConferenceService conferenceService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private ConferenceFactoryForTest conferenceFactoryForTest = new ConferenceFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Conference> list = new ArrayList<Conference>();
		when(conferenceService.findAll()).thenReturn(list);
		
		// When
		String viewName = conferenceController.list(model);
		
		// Then
		assertEquals("conference/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = conferenceController.formForCreate(model);
		
		// Then
		assertEquals("conference/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Conference)modelMap.get("conference")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/conference/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Conference conference = conferenceFactoryForTest.newConference();
		Integer id = conference.getId();
		when(conferenceService.findById(id)).thenReturn(conference);
		
		// When
		String viewName = conferenceController.formForUpdate(model, id);
		
		// Then
		assertEquals("conference/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(conference, (Conference) modelMap.get("conference"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/conference/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Conference conference = conferenceFactoryForTest.newConference();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Conference conferenceCreated = new Conference();
		when(conferenceService.create(conference)).thenReturn(conferenceCreated); 
		
		// When
		String viewName = conferenceController.create(conference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/conference/form/"+conference.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(conferenceCreated, (Conference) modelMap.get("conference"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Conference conference = conferenceFactoryForTest.newConference();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = conferenceController.create(conference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("conference/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(conference, (Conference) modelMap.get("conference"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/conference/create", modelMap.get("saveAction"));
		
	}

	@Test
	public void createException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Conference conference = conferenceFactoryForTest.newConference();
		
		Exception exception = new RuntimeException("test exception");
		when(conferenceService.create(conference)).thenThrow(exception);
		
		// When
		String viewName = conferenceController.create(conference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("conference/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(conference, (Conference) modelMap.get("conference"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/conference/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "conference.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Conference conference = conferenceFactoryForTest.newConference();
		Integer id = conference.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Conference conferenceSaved = new Conference();
		conferenceSaved.setId(id);
		when(conferenceService.update(conference)).thenReturn(conferenceSaved); 
		
		// When
		String viewName = conferenceController.update(conference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/conference/form/"+conference.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(conferenceSaved, (Conference) modelMap.get("conference"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Conference conference = conferenceFactoryForTest.newConference();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = conferenceController.update(conference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("conference/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(conference, (Conference) modelMap.get("conference"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/conference/update", modelMap.get("saveAction"));
		
	}

	@Test
	public void updateException() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);

		Conference conference = conferenceFactoryForTest.newConference();
		
		Exception exception = new RuntimeException("test exception");
		when(conferenceService.update(conference)).thenThrow(exception);
		
		// When
		String viewName = conferenceController.update(conference, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("conference/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(conference, (Conference) modelMap.get("conference"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/conference/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "conference.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Conference conference = conferenceFactoryForTest.newConference();
		Integer id = conference.getId();
		
		// When
		String viewName = conferenceController.delete(redirectAttributes, id);
		
		// Then
		verify(conferenceService).delete(id);
		assertEquals("redirect:/conference", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Conference conference = conferenceFactoryForTest.newConference();
		Integer id = conference.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(conferenceService).delete(id);
		
		// When
		String viewName = conferenceController.delete(redirectAttributes, id);
		
		// Then
		verify(conferenceService).delete(id);
		assertEquals("redirect:/conference", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "conference.error.delete", exception);
	}
	
	
}
