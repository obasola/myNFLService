/*
 * Created on 5 Jun 2016 ( Time 14:55:25 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.test.persistence;


import com.kumasi.mynfl.domain.jpa.TeamEntity;
import com.kumasi.mynfl.mock.TeamEntityMock;
import com.kumasi.mynfl.persistence.PersistenceServiceProvider;
import com.kumasi.mynfl.persistence.services.TeamPersistence;

import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit test case for Team persistence service
 * 
 * @author Telosys Tools Generator
 *
 */
public class TeamPersistenceTest 
{
	@Test
	public void test1() {
		
		System.out.println("Test count ..." );
		
		TeamPersistence service = PersistenceServiceProvider.getService(TeamPersistence.class);
		System.out.println("CountAll = " + service.countAll() );
	}
	
	@Test
	public void test2() {
		
		System.out.println("Test Team persistence : delete + load ..." );
		
		TeamPersistence service = PersistenceServiceProvider.getService(TeamPersistence.class);
		
		TeamEntityMock mock = new TeamEntityMock();
		
		// TODO : set primary key values here 
		process( service, mock, 0  );
		// process( service, mock, ... );
	}

	private void process(TeamPersistence service, TeamEntityMock mock, Integer id ) {
		System.out.println("----- "  );
		System.out.println(" . load : " );
		TeamEntity entity = service.load( id );
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

			// This entity references the following entities : 
			// . Division
			/* Insert only if references are OK
			// Try to insert the new instance
			System.out.println(" . insert : " + entity );
			service.insert(entity);
			System.out.println("   inserted : " + entity );
			*/

			System.out.println(" . delete : " );
			boolean deleted = service.delete( id );
			System.out.println("   deleted = " + deleted);
		}		
	}
}
