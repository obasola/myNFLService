/*
 * Created on 5 Jun 2016 ( Time 14:55:25 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.persistence.services.fake;

import java.util.List;
import java.util.Map;

import com.kumasi.mynfl.domain.jpa.ScheduleEntity;
import com.kumasi.mynfl.persistence.commons.fake.GenericFakeService;
import com.kumasi.mynfl.persistence.services.SchedulePersistence;

/**
 * Fake persistence service implementation ( entity "Schedule" )
 *
 * @author Telosys Tools Generator
 */
public class SchedulePersistenceFAKE extends GenericFakeService<ScheduleEntity> implements SchedulePersistence {

	public SchedulePersistenceFAKE () {
		super(ScheduleEntity.class);
	}
	
	protected ScheduleEntity buildEntity(int index) {
		ScheduleEntity entity = new ScheduleEntity();
		// Init fields with mock values
		entity.setId( nextInteger() ) ;
		entity.setYear( nextString() ) ;
		entity.setGamedate( nextString() ) ;
		entity.setGamelocation( nextString() ) ;
		entity.setOpponent( nextString() ) ;
		entity.setOpponentScore( nextInteger() ) ;
		entity.setTeamScore( nextInteger() ) ;
		entity.setOutcome( nextString() ) ;
		return entity ;
	}
	
	
	public boolean delete(ScheduleEntity entity) {
		log("delete ( ScheduleEntity : " + entity + ")" ) ;
		return true;
	}

	public boolean delete( Integer id ) {
		log("delete ( PK )") ;
		return true;
	}

	public void insert(ScheduleEntity entity) {
		log("insert ( ScheduleEntity : " + entity + ")" ) ;
	}

	public ScheduleEntity load( Integer id ) {
		log("load ( PK )") ;
		return buildEntity(1) ; 
	}

	public List<ScheduleEntity> loadAll() {
		log("loadAll()") ;
		return buildList(40) ;
	}

	public List<ScheduleEntity> loadByNamedQuery(String queryName) {
		log("loadByNamedQuery ( '" + queryName + "' )") ;
		return buildList(20) ;
	}

	public List<ScheduleEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		log("loadByNamedQuery ( '" + queryName + "', parameters )") ;
		return buildList(10) ;
	}

	public ScheduleEntity save(ScheduleEntity entity) {
		log("insert ( ScheduleEntity : " + entity + ")" ) ;
		return entity;
	}

	public List<ScheduleEntity> search(Map<String, Object> criteria) {
		log("search (criteria)" ) ;
		return buildList(15) ;
	}

	@Override
	public long countAll() {
		return 0 ;
	}

}
