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
import com.kumasi.mynfl.domain.Schedule;
import com.kumasi.mynfl.domain.Team;
import com.kumasi.mynfl.domain.ScheduleType;
import com.kumasi.mynfl.test.ScheduleFactoryForTest;
import com.kumasi.mynfl.test.TeamFactoryForTest;
import com.kumasi.mynfl.test.ScheduleTypeFactoryForTest;

//--- Services 
import com.kumasi.mynfl.business.service.ScheduleService;
import com.kumasi.mynfl.business.service.TeamService;
import com.kumasi.mynfl.business.service.ScheduleTypeService;

//--- List Items 
import com.kumasi.mynfl.web.listitem.TeamListItem;
import com.kumasi.mynfl.web.listitem.ScheduleTypeListItem;

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
public class ScheduleControllerTest {
	
	@InjectMocks
	private ScheduleController scheduleController;
	@Mock
	private ScheduleService scheduleService;
	@Mock
	private MessageHelper messageHelper;
	@Mock
	private MessageSource messageSource;
	@Mock
	private TeamService teamService; // Injected by Spring
	@Mock
	private ScheduleTypeService scheduleTypeService; // Injected by Spring

	private ScheduleFactoryForTest scheduleFactoryForTest = new ScheduleFactoryForTest();
	private TeamFactoryForTest teamFactoryForTest = new TeamFactoryForTest();
	private ScheduleTypeFactoryForTest scheduleTypeFactoryForTest = new ScheduleTypeFactoryForTest();

	List<Team> teams = new ArrayList<Team>();
	List<ScheduleType> scheduleTypes = new ArrayList<ScheduleType>();

	private void givenPopulateModel() {
		Team team1 = teamFactoryForTest.newTeam();
		Team team2 = teamFactoryForTest.newTeam();
		List<Team> teams = new ArrayList<Team>();
		teams.add(team1);
		teams.add(team2);
		when(teamService.findAll()).thenReturn(teams);

		ScheduleType scheduleType1 = scheduleTypeFactoryForTest.newScheduleType();
		ScheduleType scheduleType2 = scheduleTypeFactoryForTest.newScheduleType();
		List<ScheduleType> scheduleTypes = new ArrayList<ScheduleType>();
		scheduleTypes.add(scheduleType1);
		scheduleTypes.add(scheduleType2);
		when(scheduleTypeService.findAll()).thenReturn(scheduleTypes);

	}

	@Test
	public void list() {
		// Given
		Model model = new ExtendedModelMap();
		
		List<Schedule> list = new ArrayList<Schedule>();
		when(scheduleService.findAll()).thenReturn(list);
		
		// When
		String viewName = scheduleController.list(model);
		
		// Then
		assertEquals("schedule/list", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("list"));
	}
	
	@Test
	public void formForCreate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		// When
		String viewName = scheduleController.formForCreate(model);
		
		// Then
		assertEquals("schedule/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertNull(((Schedule)modelMap.get("schedule")).getId());
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/schedule/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
		@SuppressWarnings("unchecked")
		List<ScheduleTypeListItem> scheduleTypeListItems = (List<ScheduleTypeListItem>) modelMap.get("listOfScheduleTypeItems");
		assertEquals(2, scheduleTypeListItems.size());
		
	}
	
	@Test
	public void formForUpdate() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Schedule schedule = scheduleFactoryForTest.newSchedule();
		Integer id = schedule.getId();
		when(scheduleService.findById(id)).thenReturn(schedule);
		
		// When
		String viewName = scheduleController.formForUpdate(model, id);
		
		// Then
		assertEquals("schedule/form", viewName);
		
		Map<String,?> modelMap = model.asMap();
		
		assertEquals(schedule, (Schedule) modelMap.get("schedule"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/schedule/update", modelMap.get("saveAction"));
		
		List<ScheduleTypeListItem> scheduleTypeListItems = (List<ScheduleTypeListItem>) modelMap.get("listOfScheduleTypeItems");
		assertEquals(2, scheduleTypeListItems.size());
		
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
	}
	
	@Test
	public void createOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Schedule schedule = scheduleFactoryForTest.newSchedule();
		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Schedule scheduleCreated = new Schedule();
		when(scheduleService.create(schedule)).thenReturn(scheduleCreated); 
		
		// When
		String viewName = scheduleController.create(schedule, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/schedule/form/"+schedule.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(scheduleCreated, (Schedule) modelMap.get("schedule"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void createBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Schedule schedule = scheduleFactoryForTest.newSchedule();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = scheduleController.create(schedule, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("schedule/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(schedule, (Schedule) modelMap.get("schedule"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/schedule/create", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
		@SuppressWarnings("unchecked")
		List<ScheduleTypeListItem> scheduleTypeListItems = (List<ScheduleTypeListItem>) modelMap.get("listOfScheduleTypeItems");
		assertEquals(2, scheduleTypeListItems.size());
		
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

		Schedule schedule = scheduleFactoryForTest.newSchedule();
		
		Exception exception = new RuntimeException("test exception");
		when(scheduleService.create(schedule)).thenThrow(exception);
		
		// When
		String viewName = scheduleController.create(schedule, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("schedule/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(schedule, (Schedule) modelMap.get("schedule"));
		assertEquals("create", modelMap.get("mode"));
		assertEquals("/schedule/create", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "schedule.error.create", exception);
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
		@SuppressWarnings("unchecked")
		List<ScheduleTypeListItem> scheduleTypeListItems = (List<ScheduleTypeListItem>) modelMap.get("listOfScheduleTypeItems");
		assertEquals(2, scheduleTypeListItems.size());
		
	}

	@Test
	public void updateOk() {
		// Given
		Model model = new ExtendedModelMap();
		
		Schedule schedule = scheduleFactoryForTest.newSchedule();
		Integer id = schedule.getId();

		BindingResult bindingResult = mock(BindingResult.class);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		Schedule scheduleSaved = new Schedule();
		scheduleSaved.setId(id);
		when(scheduleService.update(schedule)).thenReturn(scheduleSaved); 
		
		// When
		String viewName = scheduleController.update(schedule, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("redirect:/schedule/form/"+schedule.getId(), viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(scheduleSaved, (Schedule) modelMap.get("schedule"));
		assertEquals(null, modelMap.get("mode"));
		assertEquals(null, modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"save.ok"));
	}

	@Test
	public void updateBindingResultErrors() {
		// Given
		Model model = new ExtendedModelMap();
		
		givenPopulateModel();
		
		Schedule schedule = scheduleFactoryForTest.newSchedule();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
		
		// When
		String viewName = scheduleController.update(schedule, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("schedule/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(schedule, (Schedule) modelMap.get("schedule"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/schedule/update", modelMap.get("saveAction"));
		
		@SuppressWarnings("unchecked")
		List<ScheduleTypeListItem> scheduleTypeListItems = (List<ScheduleTypeListItem>) modelMap.get("listOfScheduleTypeItems");
		assertEquals(2, scheduleTypeListItems.size());
		
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

		Schedule schedule = scheduleFactoryForTest.newSchedule();
		
		Exception exception = new RuntimeException("test exception");
		when(scheduleService.update(schedule)).thenThrow(exception);
		
		// When
		String viewName = scheduleController.update(schedule, bindingResult, model, redirectAttributes, httpServletRequest);
		
		// Then
		assertEquals("schedule/form", viewName);

		Map<String,?> modelMap = model.asMap();
		
		assertEquals(schedule, (Schedule) modelMap.get("schedule"));
		assertEquals("update", modelMap.get("mode"));
		assertEquals("/schedule/update", modelMap.get("saveAction"));
		
		Mockito.verify(messageHelper).addException(model, "schedule.error.update", exception);
		
		@SuppressWarnings("unchecked")
		List<ScheduleTypeListItem> scheduleTypeListItems = (List<ScheduleTypeListItem>) modelMap.get("listOfScheduleTypeItems");
		assertEquals(2, scheduleTypeListItems.size());
		
		@SuppressWarnings("unchecked")
		List<TeamListItem> teamListItems = (List<TeamListItem>) modelMap.get("listOfTeamItems");
		assertEquals(2, teamListItems.size());
		
	}
	

	@Test
	public void deleteOK() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Schedule schedule = scheduleFactoryForTest.newSchedule();
		Integer id = schedule.getId();
		
		// When
		String viewName = scheduleController.delete(redirectAttributes, id);
		
		// Then
		verify(scheduleService).delete(id);
		assertEquals("redirect:/schedule", viewName);
		Mockito.verify(messageHelper).addMessage(redirectAttributes, new Message(MessageType.SUCCESS,"delete.ok"));
	}

	@Test
	public void deleteException() {
		// Given
		RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
		
		Schedule schedule = scheduleFactoryForTest.newSchedule();
		Integer id = schedule.getId();
		
		Exception exception = new RuntimeException("test exception");
		doThrow(exception).when(scheduleService).delete(id);
		
		// When
		String viewName = scheduleController.delete(redirectAttributes, id);
		
		// Then
		verify(scheduleService).delete(id);
		assertEquals("redirect:/schedule", viewName);
		Mockito.verify(messageHelper).addException(redirectAttributes, "schedule.error.delete", exception);
	}
	
	
}
