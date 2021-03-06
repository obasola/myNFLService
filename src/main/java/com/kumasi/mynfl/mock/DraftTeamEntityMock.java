
/*
 * Created on 5 Jun 2016 ( Time 14:55:24 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.mynfl.domain.jpa.DraftTeamEntity;
import com.kumasi.mynfl.mock.tool.MockValues;

public class DraftTeamEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public DraftTeamEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public DraftTeamEntity createInstance( Integer id ) {
		DraftTeamEntity entity = new DraftTeamEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setTeamCode( mockValues.nextString(3) ) ; // java.lang.String 
		entity.setTeamName( mockValues.nextString(45) ) ; // java.lang.String 
		// Init Link fields (if any)
		// setListOfDraftRound( TODO ) ; // List<DraftRound> 
		// setDraft( TODO ) ; // Draft 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<DraftTeamEntity> createList(int count) {
		List<DraftTeamEntity> list = new LinkedList<DraftTeamEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
