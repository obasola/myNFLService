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
import com.kumasi.mynfl.domain.ScheduleType;
import com.kumasi.mynfl.business.service.ScheduleTypeService;
import com.kumasi.mynfl.web.listitem.ScheduleTypeListItem;

/**
 * Spring MVC controller for 'ScheduleType' management.
 */
@Controller
public class ScheduleTypeRestController {

	@Resource
	private ScheduleTypeService scheduleTypeService;
	
	@RequestMapping( value="/items/scheduleType",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ScheduleTypeListItem> findAllAsListItems() {
		List<ScheduleType> list = scheduleTypeService.findAll();
		List<ScheduleTypeListItem> items = new LinkedList<ScheduleTypeListItem>();
		for ( ScheduleType scheduleType : list ) {
			items.add(new ScheduleTypeListItem( scheduleType ) );
		}
		return items;
	}
	
	@RequestMapping( value="/scheduleType",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ScheduleType> findAll() {
		return scheduleTypeService.findAll();
	}

	@RequestMapping( value="/scheduleType/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ScheduleType findOne(@PathVariable("id") Integer id) {
		return scheduleTypeService.findById(id);
	}
	
	@RequestMapping( value="/scheduleType",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ScheduleType create(@RequestBody ScheduleType scheduleType) {
		return scheduleTypeService.create(scheduleType);
	}

	@RequestMapping( value="/scheduleType/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ScheduleType update(@PathVariable("id") Integer id, @RequestBody ScheduleType scheduleType) {
		return scheduleTypeService.update(scheduleType);
	}

	@RequestMapping( value="/scheduleType/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		scheduleTypeService.delete(id);
	}
	
}
