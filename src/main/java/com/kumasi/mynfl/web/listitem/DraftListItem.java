/*
 * Created on 5 Jun 2016 ( Time 14:55:48 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.web.listitem;

import com.kumasi.mynfl.domain.Draft;
import com.kumasi.mynfl.web.common.ListItem;

public class DraftListItem implements ListItem {

	private final String value ;
	private final String label ;
	
	public DraftListItem(Draft draft) {
		super();

		this.value = ""
			 + draft.getId()
		;

		//TODO : Define here the attributes to be displayed as the label
		this.label = draft.toString();
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
