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
import com.kumasi.mynfl.domain.PlayerStat;
import com.kumasi.mynfl.business.service.PlayerStatService;
import com.kumasi.mynfl.web.listitem.PlayerStatListItem;

/**
 * Spring MVC controller for 'PlayerStat' management.
 */
@Controller
public class PlayerStatRestController {

	@Resource
	private PlayerStatService playerStatService;
	
	@RequestMapping( value="/items/playerStat",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<PlayerStatListItem> findAllAsListItems() {
		List<PlayerStat> list = playerStatService.findAll();
		List<PlayerStatListItem> items = new LinkedList<PlayerStatListItem>();
		for ( PlayerStat playerStat : list ) {
			items.add(new PlayerStatListItem( playerStat ) );
		}
		return items;
	}
	
	@RequestMapping( value="/playerStat",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<PlayerStat> findAll() {
		return playerStatService.findAll();
	}

	@RequestMapping( value="/playerStat/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public PlayerStat findOne(@PathVariable("id") Integer id) {
		return playerStatService.findById(id);
	}
	
	@RequestMapping( value="/playerStat",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public PlayerStat create(@RequestBody PlayerStat playerStat) {
		return playerStatService.create(playerStat);
	}

	@RequestMapping( value="/playerStat/{id}",
			method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public PlayerStat update(@PathVariable("id") Integer id, @RequestBody PlayerStat playerStat) {
		return playerStatService.update(playerStat);
	}

	@RequestMapping( value="/playerStat/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public void delete(@PathVariable("id") Integer id) {
		playerStatService.delete(id);
	}
	
}