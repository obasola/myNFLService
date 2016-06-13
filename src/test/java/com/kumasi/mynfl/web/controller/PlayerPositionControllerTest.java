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
import com.kumasi.mynfl.domain.PlayerPosition;
import com.kumasi.mynfl.test.PlayerPositionFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.PlayerPositionService;


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
public class PlayerPositionControllerTest {
	
	@InjectMocks
	private PlayerPositionController playerPositionController;
	@Mock
	private PlayerPositionService playerPositionService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;

	private PlayerPositionFactoryForTest playerPositionFactoryForTest = new PlayerPositionFactoryForTest();


	private void givenPopulateModel() {
	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<PlayerPosition> list = new ArrayList<PlayerPosition>();
		when(playerPositionService.findAll()).thenReturn(list);
		
		// When
		String viewName = playerPositionController.list(model);
		
		// Then
		assertEquals("playerPosition/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = playerPositionController.formForCreate(model);
		
		// Then
		assertEquals("playerPosition/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((PlayerPosition)modelMap.get("playerPosition")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/playerPosition/create", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PlayerPosition playerPosition = playerPositionFactoryForTest.newPlayerPosition();
		Integer id = playerPosition.getId();
		when(playerPositionService.findById(id)).thenReturn(playerPosition);
		
		// When
		String viewName = playerPositionController.formForUpdate(model, id);
		
		// Then
		assertEquals("playerPosition/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerPosition, (PlayerPosition) modelMap.get("playerPosition"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/playerPosition/update", modelMap.get("saveAction"));
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PlayerPosition playerPosition = playerPositionFactoryForTest.newPlayerPosition();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PlayerPosition playerPositionCreated = new PlayerPosition();
		when(playerPositionService.create(playerPosition)).thenReturn(playerPositionCreated); 
		
		// When
		String viewName = playerPositionController.create(playerPosition, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/playerPosition/form/"+playerPosition.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerPositionCreated, (PlayerPosition) modelMap.get("playerPosition"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PlayerPosition playerPosition = playerPositionFactoryForTest.newPlayerPosition();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = playerPositionController.create(playerPosition, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerPosition/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerPosition, (PlayerPosition) modelMap.get("playerPosition"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/playerPosition/create", modelMap.get("saveAction"));
		
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

		PlayerPosition playerPosition = playerPositionFactoryForTest.newPlayerPosition();
		
		Exception exception = new RuntimeException("test exception");
		when(playerPositionService.create(playerPosition)).thenThrow(exception);
		
		// When
		String viewName = playerPositionController.create(playerPosition, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerPosition/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerPosition, (PlayerPosition) modelMap.get("playerPosition"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/playerPosition/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "playerPosition.error.create", exception);
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PlayerPosition playerPosition = playerPositionFactoryForTest.newPlayerPosition();
		Integer id = playerPosition.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PlayerPosition playerPositionSaved = new PlayerPosition();
		playerPositionSaved.setId(id);
		when(playerPositionService.update(playerPosition)).thenReturn(playerPositionSaved); 
		
		// When
		String viewName = playerPositionController.update(playerPosition, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/playerPosition/form/"+playerPosition.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerPositionSaved, (PlayerPosition) modelMap.get("playerPosition"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PlayerPosition playerPosition = playerPositionFactoryForTest.newPlayerPosition();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = playerPositionController.update(playerPosition, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerPosition/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerPosition, (PlayerPosition) modelMap.get("playerPosition"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/playerPosition/update", modelMap.get("saveAction"));
		
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

		PlayerPosition playerPosition = playerPositionFactoryForTest.newPlayerPosition();
		
		Exception exception = new RuntimeException("test exception");
		when(playerPositionService.update(playerPosition)).thenThrow(exception);
		
		// When
		String viewName = playerPositionController.update(playerPosition, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerPosition/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerPosition, (PlayerPosition) modelMap.get("playerPosition"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/playerPosition/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "playerPosition.error.update", exception);
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PlayerPosition playerPosition = playerPositionFactoryForTest.newPlayerPosition();
		Integer id = playerPosition.getId();
		
		// When
		String viewName = playerPositionController.delete(redirectAttributes, id);
		
		// Then
		verify(playerPositionService).delete(id);
		assertEquals("redirect:/playerPosition", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PlayerPosition playerPosition = playerPositionFactoryForTest.newPlayerPosition();
		Integer id = playerPosition.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(playerPositionService).delete(id);
		
		// When
		String viewName = playerPositionController.delete(redirectAttributes, id);
		
		// Then
		verify(playerPositionService).delete(id);
		assertEquals("redirect:/playerPosition", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "playerPosition.error.delete", exception);
	}
	
	
}
