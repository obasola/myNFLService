/*
 * Created on 5 Jun 2016 ( Time 14:55:49 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.domain;

import java.io.Serializable;

import javax.validation.constraints.*;

public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------------------------
	// ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
	// ----------------------------------------------------------------------
	@NotNull
	private Integer id;

	// ----------------------------------------------------------------------
	// ENTITY DATA FIELDS
	// ----------------------------------------------------------------------
	@Size(max = 45)
	private String year;

	@Size(max = 45)
	private String gamedate;

	@Size(max = 45)
	private String gamelocation;

	@Size(max = 45)
	private String opponent;

	private Integer opponentScore;

	private Integer teamScore;

	@Size(max = 1)
	private String outcome;

	@NotNull
	private Integer scheduleTypeId;

	@NotNull
	private Integer teamId;

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
	public void setYear(String year) {
		this.year = year;
	}

	public String getYear() {
		return this.year;
	}

	public void setGamedate(String gamedate) {
		this.gamedate = gamedate;
	}

	public String getGamedate() {
		return this.gamedate;
	}

	public void setGamelocation(String gamelocation) {
		this.gamelocation = gamelocation;
	}

	public String getGamelocation() {
		return this.gamelocation;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public String getOpponent() {
		return this.opponent;
	}

	public void setOpponentScore(Integer opponentScore) {
		this.opponentScore = opponentScore;
	}

	public Integer getOpponentScore() {
		return this.opponentScore;
	}

	public void setTeamScore(Integer teamScore) {
		this.teamScore = teamScore;
	}

	public Integer getTeamScore() {
		return this.teamScore;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getOutcome() {
		return this.outcome;
	}

	public void setScheduleTypeId(Integer scheduleTypeId) {
		this.scheduleTypeId = scheduleTypeId;
	}

	public Integer getScheduleTypeId() {
		return this.scheduleTypeId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public Integer getTeamId() {
		return this.teamId;
	}

	// ----------------------------------------------------------------------
	// toString METHOD
	// ----------------------------------------------------------------------

	public String toString() {
		return this.year.toString();
	}

}
