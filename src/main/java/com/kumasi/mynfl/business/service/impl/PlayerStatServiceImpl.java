/*
 * Created on 5 Jun 2016 ( Time 14:55:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kumasi.mynfl.domain.PlayerStat;
import com.kumasi.mynfl.domain.jpa.PlayerStatEntity;
import java.util.Date;
import com.kumasi.mynfl.business.service.PlayerStatService;
import com.kumasi.mynfl.business.service.mapping.PlayerStatServiceMapper;
import com.kumasi.mynfl.persistence.PersistenceServiceProvider;
import com.kumasi.mynfl.persistence.services.PlayerStatPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of PlayerStatService
 */
@Component
public class PlayerStatServiceImpl implements PlayerStatService {

	private PlayerStatPersistence playerStatPersistence;

	@Resource
	private PlayerStatServiceMapper playerStatServiceMapper;
	
	public PlayerStatServiceImpl() {
		playerStatPersistence = PersistenceServiceProvider.getService(PlayerStatPersistence.class);
	}
		
	@Override
	public PlayerStat findById(Integer id) {
		PlayerStatEntity entity = playerStatPersistence.load(id);
		return playerStatServiceMapper.mapPlayerStatEntityToPlayerStat(entity);
	}

	@Override
	public List<PlayerStat> findAll() {
		List<PlayerStatEntity> entities = playerStatPersistence.loadAll();
		List<PlayerStat> beans = new ArrayList<PlayerStat>();
		for(PlayerStatEntity entity : entities) {
			beans.add(playerStatServiceMapper.mapPlayerStatEntityToPlayerStat(entity));
		}
		return beans;
	}

	@Override
	public PlayerStat save(PlayerStat playerStat) {
		return update(playerStat) ;
	}

	@Override
	public PlayerStat create(PlayerStat playerStat) {
		if(playerStatPersistence.load(playerStat.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		PlayerStatEntity playerStatEntity = new PlayerStatEntity();
		playerStatServiceMapper.mapPlayerStatToPlayerStatEntity(playerStat, playerStatEntity);
		PlayerStatEntity playerStatEntitySaved = playerStatPersistence.save(playerStatEntity);
		return playerStatServiceMapper.mapPlayerStatEntityToPlayerStat(playerStatEntitySaved);
	}

	@Override
	public PlayerStat update(PlayerStat playerStat) {
		PlayerStatEntity playerStatEntity = playerStatPersistence.load(playerStat.getId());
		playerStatServiceMapper.mapPlayerStatToPlayerStatEntity(playerStat, playerStatEntity);
		PlayerStatEntity playerStatEntitySaved = playerStatPersistence.save(playerStatEntity);
		return playerStatServiceMapper.mapPlayerStatEntityToPlayerStat(playerStatEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		playerStatPersistence.delete(id);
	}

	public PlayerStatPersistence getPlayerStatPersistence() {
		return playerStatPersistence;
	}

	public void setPlayerStatPersistence(PlayerStatPersistence playerStatPersistence) {
		this.playerStatPersistence = playerStatPersistence;
	}

	public PlayerStatServiceMapper getPlayerStatServiceMapper() {
		return playerStatServiceMapper;
	}

	public void setPlayerStatServiceMapper(PlayerStatServiceMapper playerStatServiceMapper) {
		this.playerStatServiceMapper = playerStatServiceMapper;
	}

}
