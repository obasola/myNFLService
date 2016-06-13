/*
 * Created on 5 Jun 2016 ( Time 14:55:48 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.domain;

import java.io.Serializable;

import javax.validation.constraints.*;

public class DraftRound implements Serializable {

	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------------------------
	// ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
	// ----------------------------------------------------------------------
	@NotNull
	private Integer id;

	// ----------------------------------------------------------------------
	// ENTITY DATA FIELDS
	// ----------------------------------------------------------------------

	private Integer roundNbr;

	@Size(max = 45)
	private String playerName;

	@NotNull
	private Integer draftTeamId;

	@Size(max = 45)
	private String playerPosition;

	@Size(max = 16777215)
	private String playerAnalysis;

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
	public void setRoundNbr(Integer roundNbr) {
		this.roundNbr = roundNbr;
	}

	public Integer getRoundNbr() {
		return this.roundNbr;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public void setDraftTeamId(Integer draftTeamId) {
		this.draftTeamId = draftTeamId;
	}

	public Integer getDraftTeamId() {
		return this.draftTeamId;
	}

	public void setPlayerPosition(String playerPosition) {
		this.playerPosition = playerPosition;
	}

	public String getPlayerPosition() {
		return this.playerPosition;
	}

	public void setPlayerAnalysis(String playerAnalysis) {
		this.playerAnalysis = playerAnalysis;
	}

	public String getPlayerAnalysis() {
		return this.playerAnalysis;
	}

	// ----------------------------------------------------------------------
	// toString METHOD
	// ----------------------------------------------------------------------

	public String toString() {
		return this.roundNbr.toString();
	}

}
