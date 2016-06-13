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
import com.kumasi.mynfl.domain.DraftType;
import com.kumasi.mynfl.test.DraftTypeFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.DraftTypeService;


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
public class DraftTypeControllerTest {
	
	@InjectMocks
	private DraftTypeController draftTypeController;
	@Mock
	private DraftTypeService draftTypeService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private DraftTypeFactoryForTest draftTypeFactoryForTest = new DraftTypeFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<DraftType> list = new ArrayList<DraftType>();
		when(draftTypeService.findAll()).thenReturn(list);
		
		// When
		String viewName = draftTypeController.list(model);
		
		// Then
		assertEquals("draftType/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = draftTypeController.formForCreate(model);
		
		// Then
		assertEquals("draftType/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((DraftType)modelMap.get("draftType")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draftType/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DraftType draftType = draftTypeFactoryForTest.newDraftType();
		Integer id = draftType.getId();
		when(draftTypeService.findById(id)).thenReturn(draftType);
		
		// When
		String viewName = draftTypeController.formForUpdate(model, id);
		
		// Then
		assertEquals("draftType/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftType, (DraftType) modelMap.get("draftType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draftType/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DraftType draftType = draftTypeFactoryForTest.newDraftType();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DraftType draftTypeCreated = new DraftType();
		when(draftTypeService.create(draftType)).thenReturn(draftTypeCreated); 
		
		// When
		String viewName = draftTypeController.create(draftType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/draftType/form/"+draftType.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftTypeCreated, (DraftType) modelMap.get("draftType"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DraftType draftType = draftTypeFactoryForTest.newDraftType();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = draftTypeController.create(draftType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftType, (DraftType) modelMap.get("draftType"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draftType/create", modelMap.get("saveAction"));
		
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

		DraftType draftType = draftTypeFactoryForTest.newDraftType();
		
		Exception exception = new RuntimeException("test exception");
		when(draftTypeService.create(draftType)).thenThrow(exception);
		
		// When
		String viewName = draftTypeController.create(draftType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftType, (DraftType) modelMap.get("draftType"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/draftType/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "draftType.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		DraftType draftType = draftTypeFactoryForTest.newDraftType();
		Integer id = draftType.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		DraftType draftTypeSaved = new DraftType();
		draftTypeSaved.setId(id);
		when(draftTypeService.update(draftType)).thenReturn(draftTypeSaved); 
		
		// When
		String viewName = draftTypeController.update(draftType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/draftType/form/"+draftType.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftTypeSaved, (DraftType) modelMap.get("draftType"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		DraftType draftType = draftTypeFactoryForTest.newDraftType();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = draftTypeController.update(draftType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftType, (DraftType) modelMap.get("draftType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draftType/update", modelMap.get("saveAction"));
		
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

		DraftType draftType = draftTypeFactoryForTest.newDraftType();
		
		Exception exception = new RuntimeException("test exception");
		when(draftTypeService.update(draftType)).thenThrow(exception);
		
		// When
		String viewName = draftTypeController.update(draftType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("draftType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(draftType, (DraftType) modelMap.get("draftType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/draftType/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "draftType.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DraftType draftType = draftTypeFactoryForTest.newDraftType();
		Integer id = draftType.getId();
		
		// When
		String viewName = draftTypeController.delete(redirectAttributes, id);
		
		// Then
		verify(draftTypeService).delete(id);
		assertEquals("redirect:/draftType", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		DraftType draftType = draftTypeFactoryForTest.newDraftType();
		Integer id = draftType.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(draftTypeService).delete(id);
		
		// When
		String viewName = draftTypeController.delete(redirectAttributes, id);
		
		// Then
		verify(draftTypeService).delete(id);
		assertEquals("redirect:/draftType", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "draftType.error.delete", exception);
	}
	
	
}
