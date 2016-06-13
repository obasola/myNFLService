/*
 * Created on 5 Jun 2016 ( Time 14:55:49 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service;

import java.util.List;

import com.kumasi.mynfl.domain.TeamStat;

/**
 * Business Service Interface for entity TeamStat.
 */
public interface TeamStatService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	TeamStat findById( Integer id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<TeamStat> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	TeamStat save(TeamStat entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	TeamStat update(TeamStat entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	TeamStat create(TeamStat entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Integer id );


}
