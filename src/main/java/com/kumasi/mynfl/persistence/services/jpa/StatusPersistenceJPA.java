/*
 * Created on 5 Jun 2016 ( Time 14:55:25 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */

package com.kumasi.mynfl.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.kumasi.mynfl.domain.jpa.StatusEntity;
import com.kumasi.mynfl.persistence.commons.jpa.GenericJpaService;
import com.kumasi.mynfl.persistence.commons.jpa.JpaOperation;
import com.kumasi.mynfl.persistence.services.StatusPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "Status" )
 * 
 * @author Telosys Tools Generator
 *
 */
public class StatusPersistenceJPA extends GenericJpaService<StatusEntity, Integer> implements StatusPersistence {

	/**
	 * Constructor
	 */
	public StatusPersistenceJPA() {
		super(StatusEntity.class);
	}

	@Override
	public StatusEntity load( Integer id ) {
		return super.load( id );
	}

	@Override
	public boolean delete( Integer id ) {
		return super.delete( id );
	}

	@Override
	public boolean delete(StatusEntity entity) {
		if ( entity != null ) {
			return super.delete( entity.getId() );
		}
		return false ;
	}

	@Override
	public long countAll() {
		// JPA operation definition 
		JpaOperation operation = new JpaOperation() {
			@Override
			public Object exectue(EntityManager em) throws PersistenceException {
				Query query = em.createNamedQuery("StatusEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}