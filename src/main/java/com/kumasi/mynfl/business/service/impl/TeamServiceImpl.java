/*
 * Created on 5 Jun 2016 ( Time 14:55:37 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kumasi.mynfl.domain.Team;
import com.kumasi.mynfl.domain.jpa.TeamEntity;
import java.util.List;
import com.kumasi.mynfl.business.service.TeamService;
import com.kumasi.mynfl.business.service.mapping.TeamServiceMapper;
import com.kumasi.mynfl.persistence.PersistenceServiceProvider;
import com.kumasi.mynfl.persistence.services.TeamPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of TeamService
 */
@Component
public class TeamServiceImpl implements TeamService {

	private TeamPersistence teamPersistence;

	@Resource
	private TeamServiceMapper teamServiceMapper;
	
	public TeamServiceImpl() {
		teamPersistence = PersistenceServiceProvider.getService(TeamPersistence.class);
	}
		
	@Override
	public Team findById(Integer id) {
		TeamEntity entity = teamPersistence.load(id);
		return teamServiceMapper.mapTeamEntityToTeam(entity);
	}

	@Override
	public List<Team> findAll() {
		List<TeamEntity> entities = teamPersistence.loadAll();
		List<Team> beans = new ArrayList<Team>();
		for(TeamEntity entity : entities) {
			beans.add(teamServiceMapper.mapTeamEntityToTeam(entity));
		}
		return beans;
	}

	@Override
	public Team save(Team team) {
		return update(team) ;
	}

	@Override
	public Team create(Team team) {
		if(teamPersistence.load(team.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		TeamEntity teamEntity = new TeamEntity();
		teamServiceMapper.mapTeamToTeamEntity(team, teamEntity);
		TeamEntity teamEntitySaved = teamPersistence.save(teamEntity);
		return teamServiceMapper.mapTeamEntityToTeam(teamEntitySaved);
	}

	@Override
	public Team update(Team team) {
		TeamEntity teamEntity = teamPersistence.load(team.getId());
		teamServiceMapper.mapTeamToTeamEntity(team, teamEntity);
		TeamEntity teamEntitySaved = teamPersistence.save(teamEntity);
		return teamServiceMapper.mapTeamEntityToTeam(teamEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		teamPersistence.delete(id);
	}

	public TeamPersistence getTeamPersistence() {
		return teamPersistence;
	}

	public void setTeamPersistence(TeamPersistence teamPersistence) {
		this.teamPersistence = teamPersistence;
	}

	public TeamServiceMapper getTeamServiceMapper() {
		return teamServiceMapper;
	}

	public void setTeamServiceMapper(TeamServiceMapper teamServiceMapper) {
		this.teamServiceMapper = teamServiceMapper;
	}

}
