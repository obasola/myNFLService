
/*
 * Created on 5 Jun 2016 ( Time 14:55:25 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.mynfl.domain.jpa.PlayerStatusEntity;
import com.kumasi.mynfl.mock.tool.MockValues;

public class PlayerStatusEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public PlayerStatusEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger(), mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public PlayerStatusEntity createInstance( Integer id, Integer statusId ) {
		PlayerStatusEntity entity = new PlayerStatusEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		entity.setStatusId( statusId) ;
		// Init Data fields
		entity.setCode( mockValues.nextString(45) ) ; // java.lang.String 
		entity.setName( mockValues.nextString(45) ) ; // java.lang.String 
		entity.setDescription( mockValues.nextString(2000) ) ; // java.lang.String 
		// Init Link fields (if any)
		// setPlayer( TODO ) ; // Player 
		// setStatus( TODO ) ; // Status 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<PlayerStatusEntity> createList(int count) {
		List<PlayerStatusEntity> list = new LinkedList<PlayerStatusEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
