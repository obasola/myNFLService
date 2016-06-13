package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.jpa.ConferenceEntity;

public class ConferenceEntityFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public ConferenceEntity newConferenceEntity() {

		Integer id = mockValues.nextInteger();

		ConferenceEntity conferenceEntity = new ConferenceEntity();
		conferenceEntity.setId(id);
		return conferenceEntity;
	}
	
}
