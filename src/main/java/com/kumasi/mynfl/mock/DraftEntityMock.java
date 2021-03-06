
/*
 * Created on 5 Jun 2016 ( Time 14:55:24 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.mynfl.domain.jpa.DraftEntity;
import com.kumasi.mynfl.mock.tool.MockValues;

public class DraftEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public DraftEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public DraftEntity createInstance( Integer id ) {
		DraftEntity entity = new DraftEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setYear( mockValues.nextInteger() ) ; // java.lang.Integer 
		// Init Link fields (if any)
		// setListOfDraftTeam( TODO ) ; // List<DraftTeam> 
		// setDraftType( TODO ) ; // DraftType 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<DraftEntity> createList(int count) {
		List<DraftEntity> list = new LinkedList<DraftEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
