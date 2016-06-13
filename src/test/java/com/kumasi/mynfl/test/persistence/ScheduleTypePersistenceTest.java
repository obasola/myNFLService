/*
 * Created on 5 Jun 2016 ( Time 14:55:25 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.test.persistence;


import com.kumasi.mynfl.domain.jpa.ScheduleTypeEntity;
import com.kumasi.mynfl.mock.ScheduleTypeEntityMock;
import com.kumasi.mynfl.persistence.PersistenceServiceProvider;
import com.kumasi.mynfl.persistence.services.ScheduleTypePersistence;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test case for ScheduleType persistence service
 * 
 * @author Telosys Tools Generator
 *
 */
public class ScheduleTypePersistenceTest 
{
	@Test
	public void test1() {
		
		System.out.println("Test count ..." );
		
		ScheduleTypePersistence service = PersistenceServiceProvider.getService(ScheduleTypePersistence.class);
		System.out.println("CountAll = " + service.countAll() );
	}
	
	@Test
	public void test2() {
		
		System.out.println("Test ScheduleType persistence : delete + load ..." );
		
		ScheduleTypePersistence service = PersistenceServiceProvider.getService(ScheduleTypePersistence.class);
		
		ScheduleTypeEntityMock mock = new ScheduleTypeEntityMock();
		
		// TODO : set primary key values here 
		process( service, mock, 0  );
		// process( service, mock, ... );
	}

	private void process(ScheduleTypePersistence service, ScheduleTypeEntityMock mock, Integer id ) {
		System.out.println("----- "  );
		System.out.println(" . load : " );
		ScheduleTypeEntity entity = service.load( id );
		if ( entity != null ) {
			// Found 
			System.out.println("   FOUND : " + entity );
			
			// Save (update) with the same values to avoid database integrity errors  
			System.out.println(" . save : " + entity );
			service.save(entity);
			System.out.println("   saved : " + entity );
		}
		else {
			// Not found 
			System.out.println("   NOT FOUND" );
			// Create a new instance 
			entity = mock.createInstance( id ) ;
			Assert.assertNotNull(entity);

			// No reference : insert is possible 
			// Try to insert the new instance
			System.out.println(" . insert : " + entity );
			service.insert(entity);
			System.out.println("   inserted : " + entity );

			System.out.println(" . delete : " );
			boolean deleted = service.delete( id );
			System.out.println("   deleted = " + deleted);
			Assert.assertTrue(deleted) ;
		}		
	}
}