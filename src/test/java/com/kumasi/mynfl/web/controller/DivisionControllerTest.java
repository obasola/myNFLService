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
import com.kumasi.mynfl.domain.Division;
import com.kumasi.mynfl.domain.Conference;
import com.kumasi.mynfl.test.DivisionFactoryForTest;
import com.kumasi.mynfl.test.ConferenceFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.DivisionService;
import com.kumasi.mynfl.business.service.ConferenceService;

//--- List Items 
import com.kumasi.mynfl.web.listitem.ConferenceListItem;

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
public class DivisionControllerTest {
	
	@InjectMocks
	private DivisionController divisionController;
	@Mock
	private DivisionService divisionService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private ConferenceService conferenceService; // Injected by Spring

	private DivisionFactoryForTest divisionFactoryForTest = new DivisionFactoryForTest();
	private ConferenceFactoryForTest conferenceFactoryForTest = new ConferenceFactoryForTest();

	List<Conference> conferences = new ArrayList<Conference>();

	private void givenPopulateModel() {
		Conference conference1 = conferenceFactoryForTest.newConference();
		Conference conference2 = conferenceFactoryForTest.newConference();
		List<Conference> conferences = new ArrayList<Conference>();
		conferences.add(conference1);
		conferences.add(conference2);
		when(conferenceService.findAll()).thenReturn(conferences);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Division> list = new ArrayList<Division>();
		when(divisionService.findAll()).thenReturn(list);
		
		// When
		String viewName = divisionController.list(model);
		
		// Then
		assertEquals("division/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = divisionController.formForCreate(model);
		
		// Then
		assertEquals("division/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Division)modelMap.get("division")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/division/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ConferenceListItem> conferenceListItems = (List<ConferenceListItem>) modelMap.get("listOfConferenceItems");
		assertEquals(2, conferenceListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Division division = divisionFactoryForTest.newDivision();
		Integer id = division.getId();
		when(divisionService.findById(id)).thenReturn(division);
		
		// When
		String viewName = divisionController.formForUpdate(model, id);
		
		// Then
		assertEquals("division/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(division, (Division) modelMap.get("division"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/division/update", modelMap.get("saveAction"));
		
		List<ConferenceListItem> conferenceListItems = (List<ConferenceListItem>) modelMap.get("listOfConferenceItems");
		assertEquals(2, conferenceListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Division division = divisionFactoryForTest.newDivision();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Division divisionCreated = new Division();
		when(divisionService.create(division)).thenReturn(divisionCreated); 
		
		// When
		String viewName = divisionController.create(division, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/division/form/"+division.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(divisionCreated, (Division) modelMap.get("division"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Division division = divisionFactoryForTest.newDivision();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = divisionController.create(division, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("division/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(division, (Division) modelMap.get("division"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/division/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ConferenceListItem> conferenceListItems = (List<ConferenceListItem>) modelMap.get("listOfConferenceItems");
		assertEquals(2, conferenceListItems.size());
		
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

		Division division = divisionFactoryForTest.newDivision();
		
		Exception exception = new RuntimeException("test exception");
		when(divisionService.create(division)).thenThrow(exception);
		
		// When
		String viewName = divisionController.create(division, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("division/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(division, (Division) modelMap.get("division"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/division/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "division.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<ConferenceListItem> conferenceListItems = (List<ConferenceListItem>) modelMap.get("listOfConferenceItems");
		assertEquals(2, conferenceListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Division division = divisionFactoryForTest.newDivision();
		Integer id = division.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Division divisionSaved = new Division();
		divisionSaved.setId(id);
		when(divisionService.update(division)).thenReturn(divisionSaved); 
		
		// When
		String viewName = divisionController.update(division, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/division/form/"+division.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(divisionSaved, (Division) modelMap.get("division"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Division division = divisionFactoryForTest.newDivision();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = divisionController.update(division, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("division/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(division, (Division) modelMap.get("division"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/division/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ConferenceListItem> conferenceListItems = (List<ConferenceListItem>) modelMap.get("listOfConferenceItems");
		assertEquals(2, conferenceListItems.size());
		
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

		Division division = divisionFactoryForTest.newDivision();
		
		Exception exception = new RuntimeException("test exception");
		when(divisionService.update(division)).thenThrow(exception);
		
		// When
		String viewName = divisionController.update(division, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("division/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(division, (Division) modelMap.get("division"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/division/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "division.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ConferenceListItem> conferenceListItems = (List<ConferenceListItem>) modelMap.get("listOfConferenceItems");
		assertEquals(2, conferenceListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Division division = divisionFactoryForTest.newDivision();
		Integer id = division.getId();
		
		// When
		String viewName = divisionController.delete(redirectAttributes, id);
		
		// Then
		verify(divisionService).delete(id);
		assertEquals("redirect:/division", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Division division = divisionFactoryForTest.newDivision();
		Integer id = division.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(divisionService).delete(id);
		
		// When
		String viewName = divisionController.delete(redirectAttributes, id);
		
		// Then
		verify(divisionService).delete(id);
		assertEquals("redirect:/division", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "division.error.delete", exception);
	}
	
	
}
