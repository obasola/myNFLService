/*
 * Created on 5 Jun 2016 ( Time 14:55:49 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.web.listitem;

import com.kumasi.mynfl.domain.Status;
import com.kumasi.mynfl.web.common.ListItem;

public class StatusListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public StatusListItem(Status status) {
		super();

		this.value = ""
			 + status.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = status.toString();
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
