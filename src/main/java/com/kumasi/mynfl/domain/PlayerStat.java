/*
 * Created on 5 Jun 2016 ( Time 14:55:48 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.domain;

import java.io.Serializable;

import javax.validation.constraints.*;

import java.util.Date;

public class PlayerStat implements Serializable {

	private static final long serialVersionUID = 1L;

	// ----------------------------------------------------------------------
	// ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
	// ----------------------------------------------------------------------
	@NotNull
	private Integer id;

	// ----------------------------------------------------------------------
	// ENTITY DATA FIELDS
	// ----------------------------------------------------------------------
	@Size(max = 2)
	private String playerType;

	private Integer rushingAttempts;

	private Integer passingAttempts;

	private Float passingCompletions;

	private Float yardsPerCarry;

	private Float yardsPerPass;

	private Float yardsPerCatch;

	private Integer touchdowns;

	private Integer nbrInterceptions;

	private Float nbrTackles;

	private Float nbrSacks;

	private Float nbrAssists;

	@NotNull
	private Integer playerId;

	@Size(max = 45)
	private String opponent;

	private Date gameDate;

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
	public void setPlayerType(String playerType) {
		this.playerType = playerType;
	}

	public String getPlayerType() {
		return this.playerType;
	}

	public void setRushingAttempts(Integer rushingAttempts) {
		this.rushingAttempts = rushingAttempts;
	}

	public Integer getRushingAttempts() {
		return this.rushingAttempts;
	}

	public void setPassingAttempts(Integer passingAttempts) {
		this.passingAttempts = passingAttempts;
	}

	public Integer getPassingAttempts() {
		return this.passingAttempts;
	}

	public void setPassingCompletions(Float passingCompletions) {
		this.passingCompletions = passingCompletions;
	}

	public Float getPassingCompletions() {
		return this.passingCompletions;
	}

	public void setYardsPerCarry(Float yardsPerCarry) {
		this.yardsPerCarry = yardsPerCarry;
	}

	public Float getYardsPerCarry() {
		return this.yardsPerCarry;
	}

	public void setYardsPerPass(Float yardsPerPass) {
		this.yardsPerPass = yardsPerPass;
	}

	public Float getYardsPerPass() {
		return this.yardsPerPass;
	}

	public void setYardsPerCatch(Float yardsPerCatch) {
		this.yardsPerCatch = yardsPerCatch;
	}

	public Float getYardsPerCatch() {
		return this.yardsPerCatch;
	}

	public void setTouchdowns(Integer touchdowns) {
		this.touchdowns = touchdowns;
	}

	public Integer getTouchdowns() {
		return this.touchdowns;
	}

	public void setNbrInterceptions(Integer nbrInterceptions) {
		this.nbrInterceptions = nbrInterceptions;
	}

	public Integer getNbrInterceptions() {
		return this.nbrInterceptions;
	}

	public void setNbrTackles(Float nbrTackles) {
		this.nbrTackles = nbrTackles;
	}

	public Float getNbrTackles() {
		return this.nbrTackles;
	}

	public void setNbrSacks(Float nbrSacks) {
		this.nbrSacks = nbrSacks;
	}

	public Float getNbrSacks() {
		return this.nbrSacks;
	}

	public void setNbrAssists(Float nbrAssists) {
		this.nbrAssists = nbrAssists;
	}

	public Float getNbrAssists() {
		return this.nbrAssists;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public Integer getPlayerId() {
		return this.playerId;
	}

	public void setOpponent(String opponent) {
		this.opponent = opponent;
	}

	public String getOpponent() {
		return this.opponent;
	}

	public void setGameDate(Date gameDate) {
		this.gameDate = gameDate;
	}

	public Date getGameDate() {
		return this.gameDate;
	}

	// ----------------------------------------------------------------------
	// toString METHOD
	// ----------------------------------------------------------------------

	public String toString() {
		return this.id.toString();
	}

}