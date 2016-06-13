/*
 * Created on 5 Jun 2016 ( Time 14:55:24 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.persistence.services.fake;

import java.util.List;
import java.util.Map;

import com.kumasi.mynfl.domain.jpa.PlayerStatEntity;
import com.kumasi.mynfl.persistence.commons.fake.GenericFakeService;
import com.kumasi.mynfl.persistence.services.PlayerStatPersistence;

/**
 * Fake persistence service implementation ( entity "PlayerStat" )
 *
 * @author Telosys Tools Generator
 */
public class PlayerStatPersistenceFAKE extends GenericFakeService<PlayerStatEntity> implements PlayerStatPersistence {

	public PlayerStatPersistenceFAKE () {
		super(PlayerStatEntity.class);
	}
	
	protected PlayerStatEntity buildEntity(int index) {
		PlayerStatEntity entity = new PlayerStatEntity();
		// Init fields with mock values
		entity.setId( nextInteger() ) ;
		entity.setPlayerType( nextString() ) ;
		entity.setRushingAttempts( nextInteger() ) ;
		entity.setPassingAttempts( nextInteger() ) ;
		entity.setPassingCompletions( nextFloat() ) ;
		entity.setYardsPerCarry( nextFloat() ) ;
		entity.setYardsPerPass( nextFloat() ) ;
		entity.setYardsPerCatch( nextFloat() ) ;
		entity.setTouchdowns( nextInteger() ) ;
		entity.setNbrInterceptions( nextInteger() ) ;
		entity.setNbrTackles( nextFloat() ) ;
		entity.setNbrSacks( nextFloat() ) ;
		entity.setNbrAssists( nextFloat() ) ;
		entity.setOpponent( nextString() ) ;
		entity.setGameDate( nextDate() ) ;
		return entity ;
	}
	
	
	public boolean delete(PlayerStatEntity entity) {
		log("delete ( PlayerStatEntity : " + entity + ")" ) ;
		return true;
	}

	public boolean delete( Integer id ) {
		log("delete ( PK )") ;
		return true;
	}

	public void insert(PlayerStatEntity entity) {
		log("insert ( PlayerStatEntity : " + entity + ")" ) ;
	}

	public PlayerStatEntity load( Integer id ) {
		log("load ( PK )") ;
		return buildEntity(1) ; 
	}

	public List<PlayerStatEntity> loadAll() {
		log("loadAll()") ;
		return buildList(40) ;
	}

	public List<PlayerStatEntity> loadByNamedQuery(String queryName) {
		log("loadByNamedQuery ( '" + queryName + "' )") ;
		return buildList(20) ;
	}

	public List<PlayerStatEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		log("loadByNamedQuery ( '" + queryName + "', parameters )") ;
		return buildList(10) ;
	}

	public PlayerStatEntity save(PlayerStatEntity entity) {
		log("insert ( PlayerStatEntity : " + entity + ")" ) ;
		return entity;
	}

	public List<PlayerStatEntity> search(Map<String, Object> criteria) {
		log("search (criteria)" ) ;
		return buildList(15) ;
	}

	@Override
	public long countAll() {
		return 0 ;
	}

}
