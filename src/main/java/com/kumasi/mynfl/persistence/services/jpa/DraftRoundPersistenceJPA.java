/*
 * Created on 5 Jun 2016 ( Time 14:55:24 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */

package com.kumasi.mynfl.persistence.services.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.kumasi.mynfl.domain.jpa.DraftRoundEntity;
import com.kumasi.mynfl.persistence.commons.jpa.GenericJpaService;
import com.kumasi.mynfl.persistence.commons.jpa.JpaOperation;
import com.kumasi.mynfl.persistence.services.DraftRoundPersistence;

/**
 * JPA implementation for basic persistence operations ( entity "DraftRound" )
 * 
 * @author Telosys Tools Generator
 *
 */
public class DraftRoundPersistenceJPA extends GenericJpaService<DraftRoundEntity, Integer> implements DraftRoundPersistence {

	/**
	 * Constructor
	 */
	public DraftRoundPersistenceJPA() {
		super(DraftRoundEntity.class);
	}

	@Override
	public DraftRoundEntity load( Integer id ) {
		return super.load( id );
	}

	@Override
	public boolean delete( Integer id ) {
		return super.delete( id );
	}

	@Override
	public boolean delete(DraftRoundEntity entity) {
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
				Query query = em.createNamedQuery("DraftRoundEntity.countAll");
				return query.getSingleResult() ;
			}
		} ;
		// JPA operation execution 
		return (Long) execute(operation);
	}

}