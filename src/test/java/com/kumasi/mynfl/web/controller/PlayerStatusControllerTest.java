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
import com.kumasi.mynfl.domain.PlayerStatus;
import com.kumasi.mynfl.domain.Player;
import com.kumasi.mynfl.domain.Status;
import com.kumasi.mynfl.test.PlayerStatusFactoryForTest;
import com.kumasi.mynfl.test.PlayerFactoryForTest;
import com.kumasi.mynfl.test.StatusFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.PlayerStatusService;
import com.kumasi.mynfl.business.service.PlayerService;
import com.kumasi.mynfl.business.service.StatusService;

//--- List Items 
import com.kumasi.mynfl.web.listitem.PlayerListItem;
import com.kumasi.mynfl.web.listitem.StatusListItem;

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
public class PlayerStatusControllerTest {
	
	@InjectMocks
	private PlayerStatusController playerStatusController;
	@Mock
	private PlayerStatusService playerStatusService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PlayerService playerService; // Injected by Spring
	@Mock
	private StatusService statusService; // Injected by Spring

	private PlayerStatusFactoryForTest playerStatusFactoryForTest = new PlayerStatusFactoryForTest();
	private PlayerFactoryForTest playerFactoryForTest = new PlayerFactoryForTest();
	private StatusFactoryForTest statusFactoryForTest = new StatusFactoryForTest();

	List<Player> players = new ArrayList<Player>();
	List<Status> statuss = new ArrayList<Status>();

	private void givenPopulateModel() {
		Player player1 = playerFactoryForTest.newPlayer();
		Player player2 = playerFactoryForTest.newPlayer();
		List<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		when(playerService.findAll()).thenReturn(players);

		Status status1 = statusFactoryForTest.newStatus();
		Status status2 = statusFactoryForTest.newStatus();
		List<Status> statuss = new ArrayList<Status>();
		statuss.add(status1);
		statuss.add(status2);
		when(statusService.findAll()).thenReturn(statuss);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<PlayerStatus> list = new ArrayList<PlayerStatus>();
		when(playerStatusService.findAll()).thenReturn(list);
		
		// When
		String viewName = playerStatusController.list(model);
		
		// Then
		assertEquals("playerStatus/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = playerStatusController.formForCreate(model);
		
		// Then
		assertEquals("playerStatus/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((PlayerStatus)modelMap.get("playerStatus")).getId());
		assertNull(((PlayerStatus)modelMap.get("playerStatus")).getStatusId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/playerStatus/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
		@SuppressWarnings("unchecked")
		List<StatusListItem> statusListItems = (List<StatusListItem>) modelMap.get("listOfStatusItems");
		assertEquals(2, statusListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PlayerStatus playerStatus = playerStatusFactoryForTest.newPlayerStatus();
		Integer id = playerStatus.getId();
		Integer statusId = playerStatus.getStatusId();
		when(playerStatusService.findById(id, statusId)).thenReturn(playerStatus);
		
		// When
		String viewName = playerStatusController.formForUpdate(model, id, statusId);
		
		// Then
		assertEquals("playerStatus/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStatus, (PlayerStatus) modelMap.get("playerStatus"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/playerStatus/update", modelMap.get("saveAction"));
		
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PlayerStatus playerStatus = playerStatusFactoryForTest.newPlayerStatus();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PlayerStatus playerStatusCreated = new PlayerStatus();
		when(playerStatusService.create(playerStatus)).thenReturn(playerStatusCreated); 
		
		// When
		String viewName = playerStatusController.create(playerStatus, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/playerStatus/form/"+playerStatus.getId()+"/"+playerStatus.getStatusId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStatusCreated, (PlayerStatus) modelMap.get("playerStatus"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PlayerStatus playerStatus = playerStatusFactoryForTest.newPlayerStatus();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = playerStatusController.create(playerStatus, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerStatus/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStatus, (PlayerStatus) modelMap.get("playerStatus"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/playerStatus/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
		@SuppressWarnings("unchecked")
		List<StatusListItem> statusListItems = (List<StatusListItem>) modelMap.get("listOfStatusItems");
		assertEquals(2, statusListItems.size());
		
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

		PlayerStatus playerStatus = playerStatusFactoryForTest.newPlayerStatus();
		
		Exception exception = new RuntimeException("test exception");
		when(playerStatusService.create(playerStatus)).thenThrow(exception);
		
		// When
		String viewName = playerStatusController.create(playerStatus, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerStatus/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStatus, (PlayerStatus) modelMap.get("playerStatus"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/playerStatus/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "playerStatus.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
		@SuppressWarnings("unchecked")
		List<StatusListItem> statusListItems = (List<StatusListItem>) modelMap.get("listOfStatusItems");
		assertEquals(2, statusListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		PlayerStatus playerStatus = playerStatusFactoryForTest.newPlayerStatus();
		Integer id = playerStatus.getId();
		Integer statusId = playerStatus.getStatusId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		PlayerStatus playerStatusSaved = new PlayerStatus();
		playerStatusSaved.setId(id);
		playerStatusSaved.setStatusId(statusId);
		when(playerStatusService.update(playerStatus)).thenReturn(playerStatusSaved); 
		
		// When
		String viewName = playerStatusController.update(playerStatus, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/playerStatus/form/"+playerStatus.getId()+"/"+playerStatus.getStatusId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStatusSaved, (PlayerStatus) modelMap.get("playerStatus"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		PlayerStatus playerStatus = playerStatusFactoryForTest.newPlayerStatus();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = playerStatusController.update(playerStatus, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerStatus/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStatus, (PlayerStatus) modelMap.get("playerStatus"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/playerStatus/update", modelMap.get("saveAction"));
		
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

		PlayerStatus playerStatus = playerStatusFactoryForTest.newPlayerStatus();
		
		Exception exception = new RuntimeException("test exception");
		when(playerStatusService.update(playerStatus)).thenThrow(exception);
		
		// When
		String viewName = playerStatusController.update(playerStatus, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("playerStatus/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerStatus, (PlayerStatus) modelMap.get("playerStatus"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/playerStatus/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "playerStatus.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<PlayerListItem> playerListItems = (List<PlayerListItem>) modelMap.get("listOfPlayerItems");
		assertEquals(2, playerListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PlayerStatus playerStatus = playerStatusFactoryForTest.newPlayerStatus();
		Integer id = playerStatus.getId();
		Integer statusId = playerStatus.getStatusId();
		
		// When
		String viewName = playerStatusController.delete(redirectAttributes, id, statusId);
		
		// Then
		verify(playerStatusService).delete(id, statusId);
		assertEquals("redirect:/playerStatus", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		PlayerStatus playerStatus = playerStatusFactoryForTest.newPlayerStatus();
		Integer id = playerStatus.getId();
		Integer statusId = playerStatus.getStatusId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(playerStatusService).delete(id, statusId);
		
		// When
		String viewName = playerStatusController.delete(redirectAttributes, id, statusId);
		
		// Then
		verify(playerStatusService).delete(id, statusId);
		assertEquals("redirect:/playerStatus", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "playerStatus.error.delete", exception);
	}
	
	
}
