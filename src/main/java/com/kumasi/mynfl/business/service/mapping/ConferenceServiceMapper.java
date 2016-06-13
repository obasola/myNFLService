/*
 * Created on 5 Jun 2016 ( Time 14:55:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.mynfl.domain.Conference;
import com.kumasi.mynfl.domain.jpa.ConferenceEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class ConferenceServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public ConferenceServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'ConferenceEntity' to 'Conference'
	 * @param conferenceEntity
	 */
	public Conference mapConferenceEntityToConference(ConferenceEntity conferenceEntity) {
		if(conferenceEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Conference conference = map(conferenceEntity, Conference.class);

		return conference;
	}
	
	/**
	 * Mapping from 'Conference' to 'ConferenceEntity'
	 * @param conference
	 * @param conferenceEntity
	 */
	public void mapConferenceToConferenceEntity(Conference conference, ConferenceEntity conferenceEntity) {
		if(conference == null) {
			return;
		}

		//--- Generic mapping 
		map(conference, conferenceEntity);

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