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
import com.kumasi.mynfl.domain.DraftRound;
import com.kumasi.mynfl.domain.DraftTeam;
import com.kumasi.mynfl.test.DraftRoundFactoryForTest;
import com.kumasi.mynfl.test.DraftTeamFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.DraftRoundService;
import com.kumasi.mynfl.business.service.DraftTeamService;

//--- List Items 
import com.kumasi.mynfl.web.listitem.DraftTeamListItem;

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
public class DraftRoundControllerTest {
	
	@InjectMocks
	private DraftRoundController draftRoundController;
	@Mock
	private DraftRoundService draftRoundService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private DraftTeamService draftTeamService; // Injected by Spring

	private DraftRoundFactoryForTest draftRoundFactoryForTest = new DraftRoundFactoryForTest();
	private DraftTeamFactoryForTest draftTeamFactoryForTest = new DraftTeamFactoryForTest();

	List<DraftTeam> draftTeams = new ArrayList<DraftTeam>();

	private void givenPopulateModel() {
		DraftTeam draftTeam1 = draftTeamFactoryForTest.newDraftTeam();
		DraftTeam draftTeam2 = draftTeamFactoryForTest.newDraftTeam();
		List<DraftTeam> draftTeams = new ArrayList<DraftTeam>();
		draftTeams.add(draftTeam1);
		draftTeams.add(draftTeam2);
		when(draftTeamService.findAll()).thenReturn(draftTeams);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<DraftRound> list = new ArrayList<DraftRound>();
		when(draftRoundService.findAll()).thenReturn(list);
		
		// When
		String viewName = draftRoundController.list(model);
		
		// Then
		assertEquals("draftRound/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = draftRoundController.formForCreate(model);
		
		// Then
		assertEquals("draftRound/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((DraftRound)modelMap.get("draftRound")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draftRound/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DraftTeamListItem> draftTeamListItems = (List<DraftTeamListItem>) modelMap.get("listOfDraftTeamItems");
		assertEquals(2, draftTeamListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DraftRound draftRound = draftRoundFactoryForTest.newDraftRound();
		Integer id = draftRound.getId();
		when(draftRoundService.findById(id)).thenReturn(draftRound);
		
		// When
		String viewName = draftRoundController.formForUpdate(model, id);
		
		// Then
		assertEquals("draftRound/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftRound, (DraftRound) modelMap.get("draftRound"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draftRound/update", modelMap.get("saveAction"));
		
		List<DraftTeamListItem> draftTeamListItems = (List<DraftTeamListItem>) modelMap.get("listOfDraftTeamItems");
		assertEquals(2, draftTeamListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DraftRound draftRound = draftRoundFactoryForTest.newDraftRound();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DraftRound draftRoundCreated = new DraftRound();
		when(draftRoundService.create(draftRound)).thenReturn(draftRoundCreated); 
		
		// When
		String viewName = draftRoundController.create(draftRound, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/draftRound/form/"+draftRound.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftRoundCreated, (DraftRound) modelMap.get("draftRound"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DraftRound draftRound = draftRoundFactoryForTest.newDraftRound();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = draftRoundController.create(draftRound, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftRound/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftRound, (DraftRound) modelMap.get("draftRound"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draftRound/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DraftTeamListItem> draftTeamListItems = (List<DraftTeamListItem>) modelMap.get("listOfDraftTeamItems");
		assertEquals(2, draftTeamListItems.size());
		
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

		DraftRound draftRound = draftRoundFactoryForTest.newDraftRound();
		
		Exception exception = new RuntimeException("test exception");
		when(draftRoundService.create(draftRound)).thenThrow(exception);
		
		// When
		String viewName = draftRoundController.create(draftRound, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftRound/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftRound, (DraftRound) modelMap.get("draftRound"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draftRound/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "draftRound.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<DraftTeamListItem> draftTeamListItems = (List<DraftTeamListItem>) modelMap.get("listOfDraftTeamItems");
		assertEquals(2, draftTeamListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DraftRound draftRound = draftRoundFactoryForTest.newDraftRound();
		Integer id = draftRound.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DraftRound draftRoundSaved = new DraftRound();
		draftRoundSaved.setId(id);
		when(draftRoundService.update(draftRound)).thenReturn(draftRoundSaved); 
		
		// When
		String viewName = draftRoundController.update(draftRound, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/draftRound/form/"+draftRound.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftRoundSaved, (DraftRound) modelMap.get("draftRound"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DraftRound draftRound = draftRoundFactoryForTest.newDraftRound();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = draftRoundController.update(draftRound, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftRound/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftRound, (DraftRound) modelMap.get("draftRound"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draftRound/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DraftTeamListItem> draftTeamListItems = (List<DraftTeamListItem>) modelMap.get("listOfDraftTeamItems");
		assertEquals(2, draftTeamListItems.size());
		
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

		DraftRound draftRound = draftRoundFactoryForTest.newDraftRound();
		
		Exception exception = new RuntimeException("test exception");
		when(draftRoundService.update(draftRound)).thenThrow(exception);
		
		// When
		String viewName = draftRoundController.update(draftRound, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftRound/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftRound, (DraftRound) modelMap.get("draftRound"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draftRound/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "draftRound.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<DraftTeamListItem> draftTeamListItems = (List<DraftTeamListItem>) modelMap.get("listOfDraftTeamItems");
		assertEquals(2, draftTeamListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DraftRound draftRound = draftRoundFactoryForTest.newDraftRound();
		Integer id = draftRound.getId();
		
		// When
		String viewName = draftRoundController.delete(redirectAttributes, id);
		
		// Then
		verify(draftRoundService).delete(id);
		assertEquals("redirect:/draftRound", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DraftRound draftRound = draftRoundFactoryForTest.newDraftRound();
		Integer id = draftRound.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(draftRoundService).delete(id);
		
		// When
		String viewName = draftRoundController.delete(redirectAttributes, id);
		
		// Then
		verify(draftRoundService).delete(id);
		assertEquals("redirect:/draftRound", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "draftRound.error.delete", exception);
	}
	
	
}
