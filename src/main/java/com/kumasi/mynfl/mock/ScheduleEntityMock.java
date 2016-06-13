
/*
 * Created on 5 Jun 2016 ( Time 14:55:25 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.mynfl.domain.jpa.ScheduleEntity;
import com.kumasi.mynfl.mock.tool.MockValues;

public class ScheduleEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public ScheduleEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public ScheduleEntity createInstance( Integer id ) {
		ScheduleEntity entity = new ScheduleEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setYear( mockValues.nextString(45) ) ; // java.lang.String 
		entity.setGamedate( mockValues.nextString(45) ) ; // java.lang.String 
		entity.setGamelocation( mockValues.nextString(45) ) ; // java.lang.String 
		entity.setOpponent( mockValues.nextString(45) ) ; // java.lang.String 
		entity.setOpponentScore( mockValues.nextInteger() ) ; // java.lang.Integer 
		entity.setTeamScore( mockValues.nextInteger() ) ; // java.lang.Integer 
		entity.setOutcome( mockValues.nextString(1) ) ; // java.lang.String 
		// Init Link fields (if any)
		// setTeam( TODO ) ; // Team 
		// setScheduleType( TODO ) ; // ScheduleType 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<ScheduleEntity> createList(int count) {
		List<ScheduleEntity> list = new LinkedList<ScheduleEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
