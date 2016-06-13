/*
 * Created on 5 Jun 2016 ( Time 14:55:49 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.rest.controller;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.kumasi.mynfl.domain.Schedule;
import com.kumasi.mynfl.business.service.ScheduleService;
import com.kumasi.mynfl.web.listitem.ScheduleListItem;

/**
 * Spring MVC controller for 'Schedule' management.
 */
@Controller
public class ScheduleRestController {

	@Resource
	private ScheduleService scheduleService;
	
	@RequestMapping( value="/items/schedule",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ScheduleListItem> findAllAsListItems() {
		List<Schedule> list = scheduleService.findAll();
		List<ScheduleListItem> items = new LinkedList<ScheduleListItem>();
		for ( Schedule schedule : list ) {
			items.add(new ScheduleListItem( schedule ) );
		}
		return items;
	}
	
	@RequestMapping( value="/schedule",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Schedule> findAll() {
		return scheduleService.findAll();
	}

	@RequestMapping( value="/schedule/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Schedule findOne(@PathVariable("id") Integer id) {
		return scheduleService.findById(id);
	}
	
	@RequestMapping( value="/schedule",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Schedule create(@RequestBody Schedule schedule) {
		return scheduleService.create(schedule);
	}

	@RequestMapping( value="/schedule/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Schedule update(@PathVariable("id") Integer id, @RequestBody Schedule schedule) {
		return scheduleService.update(schedule);
	}

	@RequestMapping( value="/schedule/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		scheduleService.delete(id);
	}
	
}
