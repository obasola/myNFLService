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
import com.kumasi.mynfl.domain.Player;
import com.kumasi.mynfl.domain.PlayerPosition;
import com.kumasi.mynfl.domain.Team;
import com.kumasi.mynfl.test.PlayerFactoryForTest;
import com.kumasi.mynfl.test.PlayerPositionFactoryForTest;
import com.kumasi.mynfl.test.TeamFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.PlayerService;
import com.kumasi.mynfl.business.service.PlayerPositionService;
import com.kumasi.mynfl.business.service.TeamService;

//--- List Items 
import com.kumasi.mynfl.web.listitem.PlayerPositionListItem;
import com.kumasi.mynfl.web.listitem.TeamListItem;

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
public class PlayerControllerTest {
	
	@InjectMocks
	private PlayerController playerController;
	@Mock
	private PlayerService playerService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private PlayerPositionService playerPositionService; // Injected by Spring
	@Mock
	private TeamService teamService; // Injected by Spring

	private PlayerFactoryForTest playerFactoryForTest = new PlayerFactoryForTest();
	private PlayerPositionFactoryForTest playerPositionFactoryForTest = new PlayerPositionFactoryForTest();
	private TeamFactoryForTest teamFactoryForTest = new TeamFactoryForTest();

	List<PlayerPosition> playerPositions = new ArrayList<PlayerPosition>();
	List<Team> teams = new ArrayList<Team>();

	private void givenPopulateModel() {
		PlayerPosition playerPosition1 = playerPositionFactoryForTest.newPlayerPosition();
		PlayerPosition playerPosition2 = playerPositionFactoryForTest.newPlayerPosition();
		List<PlayerPosition> playerPositions = new ArrayList<PlayerPosition>();
		playerPositions.add(playerPosition1);
		playerPositions.add(playerPosition2);
		when(playerPositionService.findAll()).thenReturn(playerPositions);

		Team team1 = teamFactoryForTest.newTeam();
		Team team2 = teamFactoryForTest.newTeam();
		List<Team> teams = new ArrayList<Team>();
		teams.add(team1);
		teams.add(team2);
		when(teamService.findAll()).thenReturn(teams);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Player> list = new ArrayList<Player>();
		when(playerService.findAll()).thenReturn(list);
		
		// When
		String viewName = playerController.list(model);
		
		// Then
		assertEquals("player/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = playerController.formForCreate(model);
		
		// Then
		assertEquals("player/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Player)modelMap.get("player")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/player/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PlayerPositionListItem> playerPositionListItems = (List<PlayerPositionListItem>) modelMap.get("listOfPlayerPositionItems");
		assertEquals(2, playerPositionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Player player = playerFactoryForTest.newPlayer();
		Integer id = player.getId();
		when(playerService.findById(id)).thenReturn(player);
		
		// When
		String viewName = playerController.formForUpdate(model, id);
		
		// Then
		assertEquals("player/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(player, (Player) modelMap.get("player"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/player/update", modelMap.get("saveAction"));
		
		List<PlayerPositionListItem> playerPositionListItems = (List<PlayerPositionListItem>) modelMap.get("listOfPlayerPositionItems");
		assertEquals(2, playerPositionListItems.size());
		
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Player player = playerFactoryForTest.newPlayer();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Player playerCreated = new Player();
		when(playerService.create(player)).thenReturn(playerCreated); 
		
		// When
		String viewName = playerController.create(player, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/player/form/"+player.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerCreated, (Player) modelMap.get("player"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Player player = playerFactoryForTest.newPlayer();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = playerController.create(player, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("player/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(player, (Player) modelMap.get("player"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/player/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PlayerPositionListItem> playerPositionListItems = (List<PlayerPositionListItem>) modelMap.get("listOfPlayerPositionItems");
		assertEquals(2, playerPositionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
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

		Player player = playerFactoryForTest.newPlayer();
		
		Exception exception = new RuntimeException("test exception");
		when(playerService.create(player)).thenThrow(exception);
		
		// When
		String viewName = playerController.create(player, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("player/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(player, (Player) modelMap.get("player"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/player/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "player.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<PlayerPositionListItem> playerPositionListItems = (List<PlayerPositionListItem>) modelMap.get("listOfPlayerPositionItems");
		assertEquals(2, playerPositionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Player player = playerFactoryForTest.newPlayer();
		Integer id = player.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Player playerSaved = new Player();
		playerSaved.setId(id);
		when(playerService.update(player)).thenReturn(playerSaved); 
		
		// When
		String viewName = playerController.update(player, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/player/form/"+player.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(playerSaved, (Player) modelMap.get("player"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Player player = playerFactoryForTest.newPlayer();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = playerController.update(player, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("player/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(player, (Player) modelMap.get("player"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/player/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<PlayerPositionListItem> playerPositionListItems = (List<PlayerPositionListItem>) modelMap.get("listOfPlayerPositionItems");
		assertEquals(2, playerPositionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
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

		Player player = playerFactoryForTest.newPlayer();
		
		Exception exception = new RuntimeException("test exception");
		when(playerService.update(player)).thenThrow(exception);
		
		// When
		String viewName = playerController.update(player, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("player/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(player, (Player) modelMap.get("player"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/player/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "player.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<PlayerPositionListItem> playerPositionListItems = (List<PlayerPositionListItem>) modelMap.get("listOfPlayerPositionItems");
		assertEquals(2, playerPositionListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Player player = playerFactoryForTest.newPlayer();
		Integer id = player.getId();
		
		// When
		String viewName = playerController.delete(redirectAttributes, id);
		
		// Then
		verify(playerService).delete(id);
		assertEquals("redirect:/player", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Player player = playerFactoryForTest.newPlayer();
		Integer id = player.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(playerService).delete(id);
		
		// When
		String viewName = playerController.delete(redirectAttributes, id);
		
		// Then
		verify(playerService).delete(id);
		assertEquals("redirect:/player", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "player.error.delete", exception);
	}
	
	
}
