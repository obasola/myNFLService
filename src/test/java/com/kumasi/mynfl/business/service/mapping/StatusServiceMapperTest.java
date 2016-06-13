/*
 * Created on 5 Jun 2016 ( Time 14:55:37 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.mapping;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.mynfl.domain.Status;
import com.kumasi.mynfl.domain.jpa.StatusEntity;
import com.kumasi.mynfl.test.MockValues;

/**
 * Test : Mapping between entity beans and display beans.
 */
public class StatusServiceMapperTest {

	private StatusServiceMapper statusServiceMapper;

	private static ModelMapper modelMapper = new ModelMapper();

	private MockValues mockValues = new MockValues();
	
	
	@BeforeClass
	public static void setUp() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	@Before
	public void before() {
		statusServiceMapper = new StatusServiceMapper();
		statusServiceMapper.setModelMapper(modelMapper);
	}
	
	/**
	 * Mapping from 'StatusEntity' to 'Status'
	 * @param statusEntity
	 */
	@Test
	public void testMapStatusEntityToStatus() {
		// Given
		StatusEntity statusEntity = new StatusEntity();
		statusEntity.setCode(mockValues.nextString(45));
		statusEntity.setName(mockValues.nextString(45));
		
		// When
		Status status = statusServiceMapper.mapStatusEntityToStatus(statusEntity);
		
		// Then
		assertEquals(statusEntity.getCode(), status.getCode());
		assertEquals(statusEntity.getName(), status.getName());
	}
	
	/**
	 * Test : Mapping from 'Status' to 'StatusEntity'
	 */
	@Test
	public void testMapStatusToStatusEntity() {
		// Given
		Status status = new Status();
		status.setCode(mockValues.nextString(45));
		status.setName(mockValues.nextString(45));

		StatusEntity statusEntity = new StatusEntity();
		
		// When
		statusServiceMapper.mapStatusToStatusEntity(status, statusEntity);
		
		// Then
		assertEquals(status.getCode(), statusEntity.getCode());
		assertEquals(status.getName(), statusEntity.getName());
	}

}