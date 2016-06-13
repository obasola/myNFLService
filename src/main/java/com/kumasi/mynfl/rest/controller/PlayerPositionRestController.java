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
import com.kumasi.mynfl.domain.PlayerPosition;
import com.kumasi.mynfl.business.service.PlayerPositionService;
import com.kumasi.mynfl.web.listitem.PlayerPositionListItem;

/**
 * Spring MVC controller for 'PlayerPosition' management.
 */
@Controller
public class PlayerPositionRestController {

	@Resource
	private PlayerPositionService playerPositionService;
	
	@RequestMapping( value="/items/playerPosition",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<PlayerPositionListItem> findAllAsListItems() {
		List<PlayerPosition> list = playerPositionService.findAll();
		List<PlayerPositionListItem> items = new LinkedList<PlayerPositionListItem>();
		for ( PlayerPosition playerPosition : list ) {
			items.add(new PlayerPositionListItem( playerPosition ) );
		}
		return items;
	}
	
	@RequestMapping( value="/playerPosition",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<PlayerPosition> findAll() {
		return playerPositionService.findAll();
	}

	@RequestMapping( value="/playerPosition/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public PlayerPosition findOne(@PathVariable("id") Integer id) {
		return playerPositionService.findById(id);
	}
	
	@RequestMapping( value="/playerPosition",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public PlayerPosition create(@RequestBody PlayerPosition playerPosition) {
		return playerPositionService.create(playerPosition);
	}

	@RequestMapping( value="/playerPosition/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public PlayerPosition update(@PathVariable("id") Integer id, @RequestBody PlayerPosition playerPosition) {
		return playerPositionService.update(playerPosition);
	}

	@RequestMapping( value="/playerPosition/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		playerPositionService.delete(id);
	}
	
}
