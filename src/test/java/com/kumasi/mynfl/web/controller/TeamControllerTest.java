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
import com.kumasi.mynfl.domain.Team;
import com.kumasi.mynfl.domain.Division;
import com.kumasi.mynfl.test.TeamFactoryForTest;
import com.kumasi.mynfl.test.DivisionFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.TeamService;
import com.kumasi.mynfl.business.service.DivisionService;

//--- List Items 
import com.kumasi.mynfl.web.listitem.DivisionListItem;

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
public class TeamControllerTest {
	
	@InjectMocks
	private TeamController teamController;
	@Mock
	private TeamService teamService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private DivisionService divisionService; // Injected by Spring

	private TeamFactoryForTest teamFactoryForTest = new TeamFactoryForTest();
	private DivisionFactoryForTest divisionFactoryForTest = new DivisionFactoryForTest();

	List<Division> divisions = new ArrayList<Division>();

	private void givenPopulateModel() {
		Division division1 = divisionFactoryForTest.newDivision();
		Division division2 = divisionFactoryForTest.newDivision();
		List<Division> divisions = new ArrayList<Division>();
		divisions.add(division1);
		divisions.add(division2);
		when(divisionService.findAll()).thenReturn(divisions);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Team> list = new ArrayList<Team>();
		when(teamService.findAll()).thenReturn(list);
		
		// When
		String viewName = teamController.list(model);
		
		// Then
		assertEquals("team/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = teamController.formForCreate(model);
		
		// Then
		assertEquals("team/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Team)modelMap.get("team")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/team/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DivisionListItem> divisionListItems = (List<DivisionListItem>) modelMap.get("listOfDivisionItems");
		assertEquals(2, divisionListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Team team = teamFactoryForTest.newTeam();
		Integer id = team.getId();
		when(teamService.findById(id)).thenReturn(team);
		
		// When
		String viewName = teamController.formForUpdate(model, id);
		
		// Then
		assertEquals("team/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(team, (Team) modelMap.get("team"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/team/update", modelMap.get("saveAction"));
		
		List<DivisionListItem> divisionListItems = (List<DivisionListItem>) modelMap.get("listOfDivisionItems");
		assertEquals(2, divisionListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Team team = teamFactoryForTest.newTeam();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Team teamCreated = new Team();
		when(teamService.create(team)).thenReturn(teamCreated); 
		
		// When
		String viewName = teamController.create(team, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/team/form/"+team.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teamCreated, (Team) modelMap.get("team"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Team team = teamFactoryForTest.newTeam();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = teamController.create(team, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("team/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(team, (Team) modelMap.get("team"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/team/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DivisionListItem> divisionListItems = (List<DivisionListItem>) modelMap.get("listOfDivisionItems");
		assertEquals(2, divisionListItems.size());
		
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

		Team team = teamFactoryForTest.newTeam();
		
		Exception exception = new RuntimeException("test exception");
		when(teamService.create(team)).thenThrow(exception);
		
		// When
		String viewName = teamController.create(team, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("team/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(team, (Team) modelMap.get("team"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/team/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "team.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<DivisionListItem> divisionListItems = (List<DivisionListItem>) modelMap.get("listOfDivisionItems");
		assertEquals(2, divisionListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Team team = teamFactoryForTest.newTeam();
		Integer id = team.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Team teamSaved = new Team();
		teamSaved.setId(id);
		when(teamService.update(team)).thenReturn(teamSaved); 
		
		// When
		String viewName = teamController.update(team, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/team/form/"+team.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(teamSaved, (Team) modelMap.get("team"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Team team = teamFactoryForTest.newTeam();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = teamController.update(team, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("team/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(team, (Team) modelMap.get("team"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/team/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<DivisionListItem> divisionListItems = (List<DivisionListItem>) modelMap.get("listOfDivisionItems");
		assertEquals(2, divisionListItems.size());
		
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

		Team team = teamFactoryForTest.newTeam();
		
		Exception exception = new RuntimeException("test exception");
		when(teamService.update(team)).thenThrow(exception);
		
		// When
		String viewName = teamController.update(team, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("team/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(team, (Team) modelMap.get("team"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/team/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "team.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<DivisionListItem> divisionListItems = (List<DivisionListItem>) modelMap.get("listOfDivisionItems");
		assertEquals(2, divisionListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Team team = teamFactoryForTest.newTeam();
		Integer id = team.getId();
		
		// When
		String viewName = teamController.delete(redirectAttributes, id);
		
		// Then
		verify(teamService).delete(id);
		assertEquals("redirect:/team", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Team team = teamFactoryForTest.newTeam();
		Integer id = team.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(teamService).delete(id);
		
		// When
		String viewName = teamController.delete(redirectAttributes, id);
		
		// Then
		verify(teamService).delete(id);
		assertEquals("redirect:/team", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "team.error.delete", exception);
	}
	
	
}
