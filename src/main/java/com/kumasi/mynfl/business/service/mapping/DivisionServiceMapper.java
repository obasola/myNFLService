/*
 * Created on 5 Jun 2016 ( Time 14:55:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.mynfl.domain.Division;
import com.kumasi.mynfl.domain.jpa.DivisionEntity;
import com.kumasi.mynfl.domain.jpa.ConferenceEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class DivisionServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public DivisionServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'DivisionEntity' to 'Division'
	 * @param divisionEntity
	 */
	public Division mapDivisionEntityToDivision(DivisionEntity divisionEntity) {
		if(divisionEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Division division = map(divisionEntity, Division.class);

		//--- Link mapping ( link to Conference )
		if(divisionEntity.getConference() != null) {
			division.setConferenceId(divisionEntity.getConference().getId());
		}
		return division;
	}
	
	/**
	 * Mapping from 'Division' to 'DivisionEntity'
	 * @param division
	 * @param divisionEntity
	 */
	public void mapDivisionToDivisionEntity(Division division, DivisionEntity divisionEntity) {
		if(division == null) {
			return;
		}

		//--- Generic mapping 
		map(division, divisionEntity);

		//--- Link mapping ( link : division )
		if( hasLinkToConference(division) ) {
			ConferenceEntity conference1 = new ConferenceEntity();
			conference1.setId( division.getConferenceId() );
			divisionEntity.setConference( conference1 );
		} else {
			divisionEntity.setConference( null );
		}

	}
	
	/**
	 * Verify that Conference id is valid.
	 * @param Conference Conference
	 * @return boolean
	 */
	private boolean hasLinkToConference(Division division) {
		if(division.getConferenceId() != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}