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
import com.kumasi.mynfl.domain.ScheduleType;
import com.kumasi.mynfl.test.ScheduleTypeFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.ScheduleTypeService;


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
public class ScheduleTypeControllerTest {
	
	@InjectMocks
	private ScheduleTypeController scheduleTypeController;
	@Mock
	private ScheduleTypeService scheduleTypeService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private ScheduleTypeFactoryForTest scheduleTypeFactoryForTest = new ScheduleTypeFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<ScheduleType> list = new ArrayList<ScheduleType>();
		when(scheduleTypeService.findAll()).thenReturn(list);
		
		// When
		String viewName = scheduleTypeController.list(model);
		
		// Then
		assertEquals("scheduleType/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = scheduleTypeController.formForCreate(model);
		
		// Then
		assertEquals("scheduleType/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((ScheduleType)modelMap.get("scheduleType")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/scheduleType/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();
		Integer id = scheduleType.getId();
		when(scheduleTypeService.findById(id)).thenReturn(scheduleType);
		
		// When
		String viewName = scheduleTypeController.formForUpdate(model, id);
		
		// Then
		assertEquals("scheduleType/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(scheduleType, (ScheduleType) modelMap.get("scheduleType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/scheduleType/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ScheduleType scheduleTypeCreated = new ScheduleType();
		when(scheduleTypeService.create(scheduleType)).thenReturn(scheduleTypeCreated); 
		
		// When
		String viewName = scheduleTypeController.create(scheduleType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/scheduleType/form/"+scheduleType.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(scheduleTypeCreated, (ScheduleType) modelMap.get("scheduleType"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = scheduleTypeController.create(scheduleType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("scheduleType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(scheduleType, (ScheduleType) modelMap.get("scheduleType"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/scheduleType/create", modelMap.get("saveAction"));
		
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

		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();
		
		Exception exception = new RuntimeException("test exception");
		when(scheduleTypeService.create(scheduleType)).thenThrow(exception);
		
		// When
		String viewName = scheduleTypeController.create(scheduleType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("scheduleType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(scheduleType, (ScheduleType) modelMap.get("scheduleType"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/scheduleType/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "scheduleType.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();
		Integer id = scheduleType.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		ScheduleType scheduleTypeSaved = new ScheduleType();
		scheduleTypeSaved.setId(id);
		when(scheduleTypeService.update(scheduleType)).thenReturn(scheduleTypeSaved); 
		
		// When
		String viewName = scheduleTypeController.update(scheduleType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/scheduleType/form/"+scheduleType.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(scheduleTypeSaved, (ScheduleType) modelMap.get("scheduleType"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = scheduleTypeController.update(scheduleType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("scheduleType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(scheduleType, (ScheduleType) modelMap.get("scheduleType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/scheduleType/update", modelMap.get("saveAction"));
		
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

		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();
		
		Exception exception = new RuntimeException("test exception");
		when(scheduleTypeService.update(scheduleType)).thenThrow(exception);
		
		// When
		String viewName = scheduleTypeController.update(scheduleType, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("scheduleType/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(scheduleType, (ScheduleType) modelMap.get("scheduleType"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/scheduleType/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "scheduleType.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();
		Integer id = scheduleType.getId();
		
		// When
		String viewName = scheduleTypeController.delete(redirectAttributes, id);
		
		// Then
		verify(scheduleTypeService).delete(id);
		assertEquals("redirect:/scheduleType", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		ScheduleType scheduleType = scheduleTypeFactoryForTest.newScheduleType();
		Integer id = scheduleType.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(scheduleTypeService).delete(id);
		
		// When
		String viewName = scheduleTypeController.delete(redirectAttributes, id);
		
		// Then
		verify(scheduleTypeService).delete(id);
		assertEquals("redirect:/scheduleType", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "scheduleType.error.delete", exception);
	}
	
	
}
