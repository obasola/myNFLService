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
import com.kumasi.mynfl.domain.Draft;
import com.kumasi.mynfl.domain.DraftType;
import com.kumasi.mynfl.test.DraftFactoryForTest;
import com.kumasi.mynfl.test.DraftTypeFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.DraftService;
import com.kumasi.mynfl.business.service.DraftTypeService;

//--- List Items 
import com.kumasi.mynfl.web.listitem.DraftTypeListItem;

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
public class DraftControllerTest {
	
	@InjectMocks
	private DraftController draftController;
	@Mock
	private DraftService draftService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private DraftTypeService draftTypeService; // Injected by Spring

	private DraftFactoryForTest draftFactoryForTest = new DraftFactoryForTest();
	private DraftTypeFactoryForTest draftTypeFactoryForTest = new DraftTypeFactoryForTest();

	List<DraftType> draftTypes = new ArrayList<DraftType>();

	private void givenPopulateModel() {
		DraftType draftType1 = draftTypeFactoryForTest.newDraftType();
		DraftType draftType2 = draftTypeFactoryForTest.newDraftType();
		List<DraftType> draftTypes = new ArrayList<DraftType>();
		draftTypes.add(draftType1);
		draftTypes.add(draftType2);
		when(draftTypeService.findAll()).thenReturn(draftTypes);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Draft> list = new ArrayList<Draft>();
		when(draftService.findAll()).thenReturn(list);
		
		// When
		String viewName = draftController.list(model);
		
		// Then
		assertEquals("draft/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = draftController.formForCreate(model);
		
		// Then
		assertEquals("draft/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Draft)modelMap.get("draft")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draft/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DraftTypeListItem> draftTypeListItems = (List<DraftTypeListItem>) modelMap.get("listOfDraftTypeItems");
		assertEquals(2, draftTypeListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Draft draft = draftFactoryForTest.newDraft();
		Integer id = draft.getId();
		when(draftService.findById(id)).thenReturn(draft);
		
		// When
		String viewName = draftController.formForUpdate(model, id);
		
		// Then
		assertEquals("draft/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draft, (Draft) modelMap.get("draft"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draft/update", modelMap.get("saveAction"));
		
		List<DraftTypeListItem> draftTypeListItems = (List<DraftTypeListItem>) modelMap.get("listOfDraftTypeItems");
		assertEquals(2, draftTypeListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Draft draft = draftFactoryForTest.newDraft();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Draft draftCreated = new Draft();
		when(draftService.create(draft)).thenReturn(draftCreated); 
		
		// When
		String viewName = draftController.create(draft, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/draft/form/"+draft.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftCreated, (Draft) modelMap.get("draft"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Draft draft = draftFactoryForTest.newDraft();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = draftController.create(draft, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draft/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draft, (Draft) modelMap.get("draft"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draft/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DraftTypeListItem> draftTypeListItems = (List<DraftTypeListItem>) modelMap.get("listOfDraftTypeItems");
		assertEquals(2, draftTypeListItems.size());
		
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

		Draft draft = draftFactoryForTest.newDraft();
		
		Exception exception = new RuntimeException("test exception");
		when(draftService.create(draft)).thenThrow(exception);
		
		// When
		String viewName = draftController.create(draft, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draft/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draft, (Draft) modelMap.get("draft"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draft/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "draft.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<DraftTypeListItem> draftTypeListItems = (List<DraftTypeListItem>) modelMap.get("listOfDraftTypeItems");
		assertEquals(2, draftTypeListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Draft draft = draftFactoryForTest.newDraft();
		Integer id = draft.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Draft draftSaved = new Draft();
		draftSaved.setId(id);
		when(draftService.update(draft)).thenReturn(draftSaved); 
		
		// When
		String viewName = draftController.update(draft, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/draft/form/"+draft.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftSaved, (Draft) modelMap.get("draft"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Draft draft = draftFactoryForTest.newDraft();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = draftController.update(draft, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draft/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draft, (Draft) modelMap.get("draft"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draft/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DraftTypeListItem> draftTypeListItems = (List<DraftTypeListItem>) modelMap.get("listOfDraftTypeItems");
		assertEquals(2, draftTypeListItems.size());
		
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

		Draft draft = draftFactoryForTest.newDraft();
		
		Exception exception = new RuntimeException("test exception");
		when(draftService.update(draft)).thenThrow(exception);
		
		// When
		String viewName = draftController.update(draft, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draft/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draft, (Draft) modelMap.get("draft"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draft/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "draft.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<DraftTypeListItem> draftTypeListItems = (List<DraftTypeListItem>) modelMap.get("listOfDraftTypeItems");
		assertEquals(2, draftTypeListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Draft draft = draftFactoryForTest.newDraft();
		Integer id = draft.getId();
		
		// When
		String viewName = draftController.delete(redirectAttributes, id);
		
		// Then
		verify(draftService).delete(id);
		assertEquals("redirect:/draft", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Draft draft = draftFactoryForTest.newDraft();
		Integer id = draft.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(draftService).delete(id);
		
		// When
		String viewName = draftController.delete(redirectAttributes, id);
		
		// Then
		verify(draftService).delete(id);
		assertEquals("redirect:/draft", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "draft.error.delete", exception);
	}
	
	
}
