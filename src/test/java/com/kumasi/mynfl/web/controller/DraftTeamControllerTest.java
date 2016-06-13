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
import com.kumasi.mynfl.domain.DraftTeam;
import com.kumasi.mynfl.domain.Draft;
import com.kumasi.mynfl.test.DraftTeamFactoryForTest;
import com.kumasi.mynfl.test.DraftFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.DraftTeamService;
import com.kumasi.mynfl.business.service.DraftService;

//--- List Items 
import com.kumasi.mynfl.web.listitem.DraftListItem;

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
public class DraftTeamControllerTest {
	
	@InjectMocks
	private DraftTeamController draftTeamController;
	@Mock
	private DraftTeamService draftTeamService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private DraftService draftService; // Injected by Spring

	private DraftTeamFactoryForTest draftTeamFactoryForTest = new DraftTeamFactoryForTest();
	private DraftFactoryForTest draftFactoryForTest = new DraftFactoryForTest();

	List<Draft> drafts = new ArrayList<Draft>();

	private void givenPopulateModel() {
		Draft draft1 = draftFactoryForTest.newDraft();
		Draft draft2 = draftFactoryForTest.newDraft();
		List<Draft> drafts = new ArrayList<Draft>();
		drafts.add(draft1);
		drafts.add(draft2);
		when(draftService.findAll()).thenReturn(drafts);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<DraftTeam> list = new ArrayList<DraftTeam>();
		when(draftTeamService.findAll()).thenReturn(list);
		
		// When
		String viewName = draftTeamController.list(model);
		
		// Then
		assertEquals("draftTeam/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = draftTeamController.formForCreate(model);
		
		// Then
		assertEquals("draftTeam/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((DraftTeam)modelMap.get("draftTeam")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draftTeam/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DraftListItem> draftListItems = (List<DraftListItem>) modelMap.get("listOfDraftItems");
		assertEquals(2, draftListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DraftTeam draftTeam = draftTeamFactoryForTest.newDraftTeam();
		Integer id = draftTeam.getId();
		when(draftTeamService.findById(id)).thenReturn(draftTeam);
		
		// When
		String viewName = draftTeamController.formForUpdate(model, id);
		
		// Then
		assertEquals("draftTeam/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftTeam, (DraftTeam) modelMap.get("draftTeam"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draftTeam/update", modelMap.get("saveAction"));
		
		List<DraftListItem> draftListItems = (List<DraftListItem>) modelMap.get("listOfDraftItems");
		assertEquals(2, draftListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DraftTeam draftTeam = draftTeamFactoryForTest.newDraftTeam();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DraftTeam draftTeamCreated = new DraftTeam();
		when(draftTeamService.create(draftTeam)).thenReturn(draftTeamCreated); 
		
		// When
		String viewName = draftTeamController.create(draftTeam, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/draftTeam/form/"+draftTeam.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftTeamCreated, (DraftTeam) modelMap.get("draftTeam"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DraftTeam draftTeam = draftTeamFactoryForTest.newDraftTeam();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = draftTeamController.create(draftTeam, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftTeam/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftTeam, (DraftTeam) modelMap.get("draftTeam"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draftTeam/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DraftListItem> draftListItems = (List<DraftListItem>) modelMap.get("listOfDraftItems");
		assertEquals(2, draftListItems.size());
		
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

		DraftTeam draftTeam = draftTeamFactoryForTest.newDraftTeam();
		
		Exception exception = new RuntimeException("test exception");
		when(draftTeamService.create(draftTeam)).thenThrow(exception);
		
		// When
		String viewName = draftTeamController.create(draftTeam, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftTeam/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftTeam, (DraftTeam) modelMap.get("draftTeam"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draftTeam/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "draftTeam.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<DraftListItem> draftListItems = (List<DraftListItem>) modelMap.get("listOfDraftItems");
		assertEquals(2, draftListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DraftTeam draftTeam = draftTeamFactoryForTest.newDraftTeam();
		Integer id = draftTeam.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DraftTeam draftTeamSaved = new DraftTeam();
		draftTeamSaved.setId(id);
		when(draftTeamService.update(draftTeam)).thenReturn(draftTeamSaved); 
		
		// When
		String viewName = draftTeamController.update(draftTeam, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/draftTeam/form/"+draftTeam.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftTeamSaved, (DraftTeam) modelMap.get("draftTeam"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DraftTeam draftTeam = draftTeamFactoryForTest.newDraftTeam();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = draftTeamController.update(draftTeam, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftTeam/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftTeam, (DraftTeam) modelMap.get("draftTeam"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draftTeam/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DraftListItem> draftListItems = (List<DraftListItem>) modelMap.get("listOfDraftItems");
		assertEquals(2, draftListItems.size());
		
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

		DraftTeam draftTeam = draftTeamFactoryForTest.newDraftTeam();
		
		Exception exception = new RuntimeException("test exception");
		when(draftTeamService.update(draftTeam)).thenThrow(exception);
		
		// When
		String viewName = draftTeamController.update(draftTeam, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftTeam/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftTeam, (DraftTeam) modelMap.get("draftTeam"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draftTeam/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "draftTeam.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<DraftListItem> draftListItems = (List<DraftListItem>) modelMap.get("listOfDraftItems");
		assertEquals(2, draftListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DraftTeam draftTeam = draftTeamFactoryForTest.newDraftTeam();
		Integer id = draftTeam.getId();
		
		// When
		String viewName = draftTeamController.delete(redirectAttributes, id);
		
		// Then
		verify(draftTeamService).delete(id);
		assertEquals("redirect:/draftTeam", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DraftTeam draftTeam = draftTeamFactoryForTest.newDraftTeam();
		Integer id = draftTeam.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(draftTeamService).delete(id);
		
		// When
		String viewName = draftTeamController.delete(redirectAttributes, id);
		
		// Then
		verify(draftTeamService).delete(id);
		assertEquals("redirect:/draftTeam", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "draftTeam.error.delete", exception);
	}
	
	
}
