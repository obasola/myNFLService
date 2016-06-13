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
import com.kumasi.mynfl.domain.Status;
import com.kumasi.mynfl.test.StatusFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.StatusService;


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
public class StatusControllerTest {
	
	@InjectMocks
	private StatusController statusController;
	@Mock
	private StatusService statusService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private StatusFactoryForTest statusFactoryForTest = new StatusFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Status> list = new ArrayList<Status>();
		when(statusService.findAll()).thenReturn(list);
		
		// When
		String viewName = statusController.list(model);
		
		// Then
		assertEquals("status/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = statusController.formForCreate(model);
		
		// Then
		assertEquals("status/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Status)modelMap.get("status")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/status/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Status status = statusFactoryForTest.newStatus();
		Integer id = status.getId();
		when(statusService.findById(id)).thenReturn(status);
		
		// When
		String viewName = statusController.formForUpdate(model, id);
		
		// Then
		assertEquals("status/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(status, (Status) modelMap.get("status"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/status/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Status status = statusFactoryForTest.newStatus();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Status statusCreated = new Status();
		when(statusService.create(status)).thenReturn(statusCreated); 
		
		// When
		String viewName = statusController.create(status, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/status/form/"+status.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(statusCreated, (Status) modelMap.get("status"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Status status = statusFactoryForTest.newStatus();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = statusController.create(status, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("status/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(status, (Status) modelMap.get("status"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/status/create", modelMap.get("saveAction"));
		
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

		Status status = statusFactoryForTest.newStatus();
		
		Exception exception = new RuntimeException("test exception");
		when(statusService.create(status)).thenThrow(exception);
		
		// When
		String viewName = statusController.create(status, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("status/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(status, (Status) modelMap.get("status"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/status/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "status.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Status status = statusFactoryForTest.newStatus();
		Integer id = status.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Status statusSaved = new Status();
		statusSaved.setId(id);
		when(statusService.update(status)).thenReturn(statusSaved); 
		
		// When
		String viewName = statusController.update(status, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/status/form/"+status.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(statusSaved, (Status) modelMap.get("status"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Status status = statusFactoryForTest.newStatus();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = statusController.update(status, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("status/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(status, (Status) modelMap.get("status"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/status/update", modelMap.get("saveAction"));
		
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

		Status status = statusFactoryForTest.newStatus();
		
		Exception exception = new RuntimeException("test exception");
		when(statusService.update(status)).thenThrow(exception);
		
		// When
		String viewName = statusController.update(status, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("status/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(status, (Status) modelMap.get("status"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/status/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "status.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Status status = statusFactoryForTest.newStatus();
		Integer id = status.getId();
		
		// When
		String viewName = statusController.delete(redirectAttributes, id);
		
		// Then
		verify(statusService).delete(id);
		assertEquals("redirect:/status", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Status status = statusFactoryForTest.newStatus();
		Integer id = status.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(statusService).delete(id);
		
		// When
		String viewName = statusController.delete(redirectAttributes, id);
		
		// Then
		verify(statusService).delete(id);
		assertEquals("redirect:/status", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "status.error.delete", exception);
	}
	
	
}
