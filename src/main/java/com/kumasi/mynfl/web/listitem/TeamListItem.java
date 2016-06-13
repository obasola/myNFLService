/*
 * Created on 5 Jun 2016 ( Time 14:55:49 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.web.listitem;

import com.kumasi.mynfl.domain.Team;
import com.kumasi.mynfl.web.common.ListItem;

public class TeamListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public TeamListItem(Team team) {
		super();

		this.value = ""
			 + team.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = team.toString();
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return label;
	}

}