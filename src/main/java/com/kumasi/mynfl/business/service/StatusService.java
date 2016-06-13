/*
 * Created on 5 Jun 2016 ( Time 14:55:49 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service;

import java.util.List;

import com.kumasi.mynfl.domain.Status;

/**
 * Business Service Interface for entity Status.
 */
public interface StatusService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Status findById( Integer id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Status> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Status save(Status entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Status update(Status entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Status create(Status entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Integer id );


}
