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
import com.kumasi.mynfl.domain.PlayerStat;
import com.kumasi.mynfl.domain.Player;
import com.kumasi.mynfl.test.PlayerStatFactoryForTest;
import com.kumasi.mynfl.test.PlayerFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.PlayerStatService;
import com.kumasi.mynfl.business.service.PlayerService;

//--- List Items 
import com.kumasi.mynfl.web.listitem.PlayerListItem;

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
public class PlayerStatControllerTest {
	
	@InjectMocks
	private PlayerStatController playerStatController;
	@Mock
	private PlayerStatService playerStatService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PlayerService playerService; // Injected by Spring

	private PlayerStatFactoryForTest playerStatFactoryForTest = new PlayerStatFactoryForTest();
	private PlayerFactoryForTest playerFactoryForTest = new PlayerFactoryForTest();

	List<Player> players = new ArrayList<Player>();

	private void givenPopulateModel() {
		Player player1 = playerFactoryForTest.newPlayer();
		Player player2 = playerFactoryForTest.newPlayer();
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		when(playerService.findAll()).thenReturn(players);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<PlayerStat> list = new ArrayList<PlayerStat>();
		when(playerStatService.findAll()).thenReturn(list);
		
		// When
		String viewName = playerStatController.list(model);
		
		// Then
		assertEquals("playerStat/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = playerStatController.formForCreate(model);
		
		// Then
		assertEquals("playerStat/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((PlayerStat)modelMap.get("playerStat")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/playerStat/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PlayerStat playerStat = playerStatFactoryForTest.newPlayerStat();
		Integer id = playerStat.getId();
		when(playerStatService.findById(id)).thenReturn(playerStat);
		
		// When
		String viewName = playerStatController.formForUpdate(model, id);
		
		// Then
		assertEquals("playerStat/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStat, (PlayerStat) modelMap.get("playerStat"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/playerStat/update", modelMap.get("saveAction"));
		
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PlayerStat playerStat = playerStatFactoryForTest.newPlayerStat();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PlayerStat playerStatCreated = new PlayerStat();
		when(playerStatService.create(playerStat)).thenReturn(playerStatCreated); 
		
		// When
		String viewName = playerStatController.create(playerStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/playerStat/form/"+playerStat.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStatCreated, (PlayerStat) modelMap.get("playerStat"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PlayerStat playerStat = playerStatFactoryForTest.newPlayerStat();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = playerStatController.create(playerStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerStat/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStat, (PlayerStat) modelMap.get("playerStat"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/playerStat/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
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

		PlayerStat playerStat = playerStatFactoryForTest.newPlayerStat();
		
		Exception exception = new RuntimeException("test exception");
		when(playerStatService.create(playerStat)).thenThrow(exception);
		
		// When
		String viewName = playerStatController.create(playerStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerStat/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStat, (PlayerStat) modelMap.get("playerStat"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/playerStat/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "playerStat.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PlayerStat playerStat = playerStatFactoryForTest.newPlayerStat();
		Integer id = playerStat.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PlayerStat playerStatSaved = new PlayerStat();
		playerStatSaved.setId(id);
		when(playerStatService.update(playerStat)).thenReturn(playerStatSaved); 
		
		// When
		String viewName = playerStatController.update(playerStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/playerStat/form/"+playerStat.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStatSaved, (PlayerStat) modelMap.get("playerStat"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PlayerStat playerStat = playerStatFactoryForTest.newPlayerStat();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = playerStatController.update(playerStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerStat/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStat, (PlayerStat) modelMap.get("playerStat"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/playerStat/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
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

		PlayerStat playerStat = playerStatFactoryForTest.newPlayerStat();
		
		Exception exception = new RuntimeException("test exception");
		when(playerStatService.update(playerStat)).thenThrow(exception);
		
		// When
		String viewName = playerStatController.update(playerStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerStat/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStat, (PlayerStat) modelMap.get("playerStat"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/playerStat/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "playerStat.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PlayerStat playerStat = playerStatFactoryForTest.newPlayerStat();
		Integer id = playerStat.getId();
		
		// When
		String viewName = playerStatController.delete(redirectAttributes, id);
		
		// Then
		verify(playerStatService).delete(id);
		assertEquals("redirect:/playerStat", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PlayerStat playerStat = playerStatFactoryForTest.newPlayerStat();
		Integer id = playerStat.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(playerStatService).delete(id);
		
		// When
		String viewName = playerStatController.delete(redirectAttributes, id);
		
		// Then
		verify(playerStatService).delete(id);
		assertEquals("redirect:/playerStat", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "playerStat.error.delete", exception);
	}
	
	
}
