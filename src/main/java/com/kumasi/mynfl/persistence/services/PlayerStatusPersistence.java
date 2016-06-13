/*
 * Created on 5 Jun 2016 ( Time 14:55:25 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.persistence.services;

import java.util.List;
import java.util.Map;

import com.kumasi.mynfl.domain.jpa.PlayerStatusEntity;

/**
 * Basic persistence operations for entity "PlayerStatus"
 * 
 * This Bean has a composite Primary Key : PlayerStatusEntityKey
 *
 * @author Telosys Tools Generator
 *
 */
public interface PlayerStatusPersistence {

	/**
	 * Deletes the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param playerStatus
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(PlayerStatusEntity playerStatus) ;

	/**
	 * Deletes the entity by its Primary Key <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param id
	 * @param statusId
	 * @return true if found and deleted, false if not found
	 */
	public boolean delete(Integer id, Integer statusId) ;

	/**
	 * Inserts the given entity and commit <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param playerStatus
	 */
	public void insert(PlayerStatusEntity playerStatus) ;

	/**
	 * Loads the entity for the given Primary Key <br>
	 * @param id
	 * @param statusId
	 * @return the entity loaded (or null if not found)
	 */
	public PlayerStatusEntity load(Integer id, Integer statusId) ;

	/**
	 * Loads ALL the entities (use with caution)
	 * @return
	 */
	public List<PlayerStatusEntity> loadAll() ;

	/**
	 * Loads a list of entities using the given "named query" without parameter 
	 * @param queryName
	 * @return
	 */
	public List<PlayerStatusEntity> loadByNamedQuery(String queryName) ;

	/**
	 * Loads a list of entities using the given "named query" with parameters 
	 * @param queryName
	 * @param queryParameters
	 * @return
	 */
	public List<PlayerStatusEntity> loadByNamedQuery(String queryName, Map<String, Object> queryParameters) ;

	/**
	 * Saves (create or update) the given entity <br>
	 * Transactional operation ( begin transaction and commit )
	 * @param playerStatus
	 * @return
	 */
	public PlayerStatusEntity save(PlayerStatusEntity playerStatus) ;

	/**
	 * Search the entities matching the given search criteria
	 * @param criteria
	 * @return
	 */
	public List<PlayerStatusEntity> search( Map<String, Object> criteria ) ;

	/**
	 * Count all the occurrences
	 * @return
	 */
	public long countAll();
	
}
