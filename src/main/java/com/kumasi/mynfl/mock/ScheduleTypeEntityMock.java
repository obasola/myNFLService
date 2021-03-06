
/*
 * Created on 5 Jun 2016 ( Time 14:55:25 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.mock;

import java.util.LinkedList;
import java.util.List;

import com.kumasi.mynfl.domain.jpa.ScheduleTypeEntity;
import com.kumasi.mynfl.mock.tool.MockValues;

public class ScheduleTypeEntityMock {

	private MockValues mockValues = new MockValues();
	
	/**
	 * Creates an instance with random Primary Key
	 * @return
	 */
	public ScheduleTypeEntity createInstance() {
		// Primary Key values

		return createInstance( mockValues.nextInteger() );
	}
	
	/**
	 * Creates an instance with a specific Primary Key
	 * @param id1
	 * @return
	 */
	public ScheduleTypeEntity createInstance( Integer id ) {
		ScheduleTypeEntity entity = new ScheduleTypeEntity();
		// Init Primary Key fields
		entity.setId( id) ;
		// Init Data fields
		entity.setCode( mockValues.nextString(45) ) ; // java.lang.String 
		entity.setDescription( mockValues.nextString(45) ) ; // java.lang.String 
		// Init Link fields (if any)
		// setListOfSchedule( TODO ) ; // List<Schedule> 
		return entity ;
	}
	
	/**
	 * Creates a list of instances
	 * @param count number of instances to be created
	 * @return
	 */
	public List<ScheduleTypeEntity> createList(int count) {
		List<ScheduleTypeEntity> list = new LinkedList<ScheduleTypeEntity>();		
		for ( int i = 1 ; i <= count ; i++ ) {
			list.add( createInstance() );
		}
		return list;
	}
}
