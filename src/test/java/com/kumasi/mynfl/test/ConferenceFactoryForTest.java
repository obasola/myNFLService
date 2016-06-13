package com.kumasi.mynfl.test;

import com.kumasi.mynfl.domain.Conference;

public class ConferenceFactoryForTest {

	private MockValues mockValues = new MockValues();
	
	public Conference newConference() {

		Integer id = mockValues.nextInteger();

		Conference conference = new Conference();
		conference.setId(id);
		return conference;
	}
	
}
