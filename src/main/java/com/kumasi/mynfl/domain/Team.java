/*
 * Created on 5 Jun 2016 ( Time 14:55:49 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.domain;

import java.io.Serializable;

import javax.validation.constraints.*;

public class Team implements Serializable {

	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------------------------
	// ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
	// ----------------------------------------------------------------------
	@NotNull
	private Integer id;

	// ----------------------------------------------------------------------
	// ENTITY DATA FIELDS
	// ----------------------------------------------------------------------
	@Size(max = 3)
	private String code;

	@Size(max = 45)
	private String name;

	@Size(max = 45)
	private String city;

	@Size(max = 45)
	private String state;

	@Size(max = 45)
	private String stadium;

	@Size(max = 45)
	private String owner;

	@Size(max = 45)
	private String headCoach;

	@NotNull
	private Integer divisionId;

	// ----------------------------------------------------------------------
	// GETTER & SETTER FOR THE KEY FIELD
	// ----------------------------------------------------------------------
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	// ----------------------------------------------------------------------
	// GETTERS & SETTERS FOR FIELDS
	// ----------------------------------------------------------------------
	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return this.city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return this.state;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public String getStadium() {
		return this.stadium;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setHeadCoach(String headCoach) {
		this.headCoach = headCoach;
	}

	public String getHeadCoach() {
		return this.headCoach;
	}

	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}

	public Integer getDivisionId() {
		return this.divisionId;
	}

	// ----------------------------------------------------------------------
	// toString METHOD
	// ----------------------------------------------------------------------

	public String toString() {
		return this.name;
	}

}
