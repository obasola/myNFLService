/*
 * Created on 5 Jun 2016 ( Time 14:55:48 )
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
import com.kumasi.mynfl.domain.Conference;
import com.kumasi.mynfl.business.service.ConferenceService;
import com.kumasi.mynfl.web.listitem.ConferenceListItem;

/**
 * Spring MVC controller for 'Conference' management.
 */
@Controller
public class ConferenceRestController {

	@Resource
	private ConferenceService conferenceService;
	
	@RequestMapping( value="/items/conference",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<ConferenceListItem> findAllAsListItems() {
		List<Conference> list = conferenceService.findAll();
		List<ConferenceListItem> items = new LinkedList<ConferenceListItem>();
		for ( Conference conference : list ) {
			items.add(new ConferenceListItem( conference ) );
		}
		return items;
	}
	
	@RequestMapping( value="/conference",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Conference> findAll() {
		return conferenceService.findAll();
	}

	@RequestMapping( value="/conference/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Conference findOne(@PathVariable("id") Integer id) {
		return conferenceService.findById(id);
	}
	
	@RequestMapping( value="/conference",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Conference create(@RequestBody Conference conference) {
		return conferenceService.create(conference);
	}

	@RequestMapping( value="/conference/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Conference update(@PathVariable("id") Integer id, @RequestBody Conference conference) {
		return conferenceService.update(conference);
	}

	@RequestMapping( value="/conference/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		conferenceService.delete(id);
	}
	
}