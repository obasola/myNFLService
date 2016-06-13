/*
 * Created on 5 Jun 2016 ( Time 14:55:24 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.persistence.services.fake;

import java.util.List;
import java.util.Map;

import com.kumasi.mynfl.domain.jpa.DivisionEntity;
import com.kumasi.mynfl.persistence.commons.fake.GenericFakeService;
import com.kumasi.mynfl.persistence.services.DivisionPersistence;

/**
 * Fake persistence service implementation ( entity "Division" )
 *
 * @author Telosys Tools Generator
 */
public class DivisionPersistenceFAKE extends GenericFakeService<DivisionEntity> implements DivisionPersistence {

	public DivisionPersistenceFAKE () {
		super(DivisionEntity.class);
	}
	
	protected DivisionEntity buildEntity(int index) {
		DivisionEntity entity = new DivisionEntity();
		// Init fields with mock values
		entity.setId( nextInteger() ) ;
		entity.setName( nextString() ) ;
		return entity ;
	}
	
	
	public boolean delete(DivisionEntity entity) {
		log("delete ( DivisionEntity : " + entity + ")" ) ;
		return true;
	}

	public boolean delete( Integer id ) {
		log("delete ( PK )") ;
		return true;
	}

	public void insert(DivisionEntity entity) {
		log("insert ( DivisionEntity : " + entity + ")" ) ;
	}

	public DivisionEntity load( Integer id ) {
		log("load ( PK )") ;
		return buildEntity(1) ; 
	}

	public List<DivisionEntity> loadAll() {
		log("loadAll()") ;
		return buildList(40) ;
	}

	public List<DivisionEntity> loadByNamedQuery(String queryName) {
		log("loadByNamedQuery ( '" + queryName + "' )") ;
		return buildList(20) ;
	}

	public List<DivisionEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) {
		log("loadByNamedQuery ( '" + queryName + "', parameters )") ;
		return buildList(10) ;
	}

	public DivisionEntity save(DivisionEntity entity) {
		log("insert ( DivisionEntity : " + entity + ")" ) ;
		return entity;
	}

	public List<DivisionEntity> search(Map<String, Object> criteria) {
		log("search (criteria)" ) ;
		return buildList(15) ;
	}

	@Override
	public long countAll() {
		return 0 ;
	}

}
