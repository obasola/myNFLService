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
import com.kumasi.mynfl.domain.Draft;
import com.kumasi.mynfl.business.service.DraftService;
import com.kumasi.mynfl.web.listitem.DraftListItem;

/**
 * Spring MVC controller for 'Draft' management.
 */
@Controller
public class DraftRestController {

	@Resource
	private DraftService draftService;
	
	@RequestMapping( value="/items/draft",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<DraftListItem> findAllAsListItems() {
		List<Draft> list = draftService.findAll();
		List<DraftListItem> items = new LinkedList<DraftListItem>();
		for ( Draft draft : list ) {
			items.add(new DraftListItem( draft ) );
		}
		return items;
	}
	
	@RequestMapping( value="/draft",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<Draft> findAll() {
		return draftService.findAll();
	}

	@RequestMapping( value="/draft/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Draft findOne(@PathVariable("id") Integer id) {
		return draftService.findById(id);
	}
	
	@RequestMapping( value="/draft",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Draft create(@RequestBody Draft draft) {
		return draftService.create(draft);
	}

	@RequestMapping( value="/draft/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public Draft update(@PathVariable("id") Integer id, @RequestBody Draft draft) {
		return draftService.update(draft);
	}

	@RequestMapping( value="/draft/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		draftService.delete(id);
	}
	
}