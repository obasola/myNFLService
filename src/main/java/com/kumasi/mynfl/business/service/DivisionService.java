/*
 * Created on 5 Jun 2016 ( Time 14:55:48 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service;

import java.util.List;

import com.kumasi.mynfl.domain.Division;

/**
 * Business Service Interface for entity Division.
 */
public interface DivisionService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param id
	 * @return entity
	 */
	Division findById( Integer id  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Division> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Division save(Division entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Division update(Division entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Division create(Division entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param id
	 */
	void delete( Integer id );


}