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
import com.kumasi.mynfl.domain.TeamStat;
import com.kumasi.mynfl.domain.Team;
import com.kumasi.mynfl.test.TeamStatFactoryForTest;
import com.kumasi.mynfl.test.TeamFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.TeamStatService;
import com.kumasi.mynfl.business.service.TeamService;

//--- List Items 
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
public class TeamStatControllerTest {
	
	@InjectMocks
	private TeamStatController teamStatController;
	@Mock
	private TeamStatService teamStatService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private TeamService teamService; // Injected by Spring

	private TeamStatFactoryForTest teamStatFactoryForTest = new TeamStatFactoryForTest();
	private TeamFactoryForTest teamFactoryForTest = new TeamFactoryForTest();

	List<Team> teams = new ArrayList<Team>();

	private void givenPopulateModel() {
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
		
		List<TeamStat> list = new ArrayList<TeamStat>();
		when(teamStatService.findAll()).thenReturn(list);
		
		// When
		String viewName = teamStatController.list(model);
		
		// Then
		assertEquals("teamStat/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = teamStatController.formForCreate(model);
		
		// Then
		assertEquals("teamStat/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((TeamStat)modelMap.get("teamStat")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/teamStat/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		TeamStat teamStat = teamStatFactoryForTest.newTeamStat();
		Integer id = teamStat.getId();
		when(teamStatService.findById(id)).thenReturn(teamStat);
		
		// When
		String viewName = teamStatController.formForUpdate(model, id);
		
		// Then
		assertEquals("teamStat/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teamStat, (TeamStat) modelMap.get("teamStat"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/teamStat/update", modelMap.get("saveAction"));
		
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		TeamStat teamStat = teamStatFactoryForTest.newTeamStat();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		TeamStat teamStatCreated = new TeamStat();
		when(teamStatService.create(teamStat)).thenReturn(teamStatCreated); 
		
		// When
		String viewName = teamStatController.create(teamStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/teamStat/form/"+teamStat.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teamStatCreated, (TeamStat) modelMap.get("teamStat"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		TeamStat teamStat = teamStatFactoryForTest.newTeamStat();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = teamStatController.create(teamStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("teamStat/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teamStat, (TeamStat) modelMap.get("teamStat"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/teamStat/create", modelMap.get("saveAction"));
		
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

		TeamStat teamStat = teamStatFactoryForTest.newTeamStat();
		
		Exception exception = new RuntimeException("test exception");
		when(teamStatService.create(teamStat)).thenThrow(exception);
		
		// When
		String viewName = teamStatController.create(teamStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("teamStat/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teamStat, (TeamStat) modelMap.get("teamStat"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/teamStat/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "teamStat.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		TeamStat teamStat = teamStatFactoryForTest.newTeamStat();
		Integer id = teamStat.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		TeamStat teamStatSaved = new TeamStat();
		teamStatSaved.setId(id);
		when(teamStatService.update(teamStat)).thenReturn(teamStatSaved); 
		
		// When
		String viewName = teamStatController.update(teamStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/teamStat/form/"+teamStat.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teamStatSaved, (TeamStat) modelMap.get("teamStat"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		TeamStat teamStat = teamStatFactoryForTest.newTeamStat();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = teamStatController.update(teamStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("teamStat/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teamStat, (TeamStat) modelMap.get("teamStat"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/teamStat/update", modelMap.get("saveAction"));
		
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

		TeamStat teamStat = teamStatFactoryForTest.newTeamStat();
		
		Exception exception = new RuntimeException("test exception");
		when(teamStatService.update(teamStat)).thenThrow(exception);
		
		// When
		String viewName = teamStatController.update(teamStat, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("teamStat/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teamStat, (TeamStat) modelMap.get("teamStat"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/teamStat/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "teamStat.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		TeamStat teamStat = teamStatFactoryForTest.newTeamStat();
		Integer id = teamStat.getId();
		
		// When
		String viewName = teamStatController.delete(redirectAttributes, id);
		
		// Then
		verify(teamStatService).delete(id);
		assertEquals("redirect:/teamStat", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		TeamStat teamStat = teamStatFactoryForTest.newTeamStat();
		Integer id = teamStat.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(teamStatService).delete(id);
		
		// When
		String viewName = teamStatController.delete(redirectAttributes, id);
		
		// Then
		verify(teamStatService).delete(id);
		assertEquals("redirect:/teamStat", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "teamStat.error.delete", exception);
	}
	
	
}
