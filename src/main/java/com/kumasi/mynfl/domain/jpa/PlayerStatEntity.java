/*
 * Created on 5 Jun 2016 ( Time 14:55:24 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a basic Primary Key (not composite) 

package com.kumasi.mynfl.domain.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.Date;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "Player_Stat"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="Player_Stat", catalog="nfldb" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="PlayerStatEntity.countAll", query="SELECT COUNT(x) FROM PlayerStatEntity x" )
} )
public class PlayerStatEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    //----------------------------------------------------------------------
    @Id
    @Column(name="id", nullable=false)
    private Integer    id           ;


    //----------------------------------------------------------------------
    // ENTITY DATA FIELDS 
    //----------------------------------------------------------------------    
    @Column(name="player_type", length=2)
    private String     playerType   ;

    @Column(name="rushing_attempts")
    private Integer    rushingAttempts ;

    @Column(name="passing_attempts")
    private Integer    passingAttempts ;

    @Column(name="passing_completions")
    private Float      passingCompletions ;

    @Column(name="yards_per_carry")
    private Float      yardsPerCarry ;

    @Column(name="yards_per_pass")
    private Float      yardsPerPass ;

    @Column(name="yards_per_catch")
    private Float      yardsPerCatch ;

    @Column(name="touchdowns")
    private Integer    touchdowns   ;

    @Column(name="nbr_interceptions")
    private Integer    nbrInterceptions ;

    @Column(name="nbr_tackles")
    private Float      nbrTackles   ;

    @Column(name="nbr_sacks")
    private Float      nbrSacks     ;

    @Column(name="nbr_assists")
    private Float      nbrAssists   ;

    @Column(name="opponent", length=45)
    private String     opponent     ;

    @Temporal(TemporalType.DATE)
    @Column(name="game_date")
    private Date       gameDate     ;

	// "playerId" (column "Player_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @ManyToOne
    @JoinColumn(name="Player_id", referencedColumnName="id")
    private PlayerEntity player      ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public PlayerStatEntity() {
		super();
    }
    
    //----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    //----------------------------------------------------------------------
    public void setId( Integer id ) {
        this.id = id ;
    }
    public Integer getId() {
        return this.id;
    }

    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    //----------------------------------------------------------------------
    //--- DATABASE MAPPING : player_type ( VARCHAR ) 
    public void setPlayerType( String playerType ) {
        this.playerType = playerType;
    }
    public String getPlayerType() {
        return this.playerType;
    }

    //--- DATABASE MAPPING : rushing_attempts ( INT ) 
    public void setRushingAttempts( Integer rushingAttempts ) {
        this.rushingAttempts = rushingAttempts;
    }
    public Integer getRushingAttempts() {
        return this.rushingAttempts;
    }

    //--- DATABASE MAPPING : passing_attempts ( INT ) 
    public void setPassingAttempts( Integer passingAttempts ) {
        this.passingAttempts = passingAttempts;
    }
    public Integer getPassingAttempts() {
        return this.passingAttempts;
    }

    //--- DATABASE MAPPING : passing_completions ( FLOAT ) 
    public void setPassingCompletions( Float passingCompletions ) {
        this.passingCompletions = passingCompletions;
    }
    public Float getPassingCompletions() {
        return this.passingCompletions;
    }

    //--- DATABASE MAPPING : yards_per_carry ( FLOAT ) 
    public void setYardsPerCarry( Float yardsPerCarry ) {
        this.yardsPerCarry = yardsPerCarry;
    }
    public Float getYardsPerCarry() {
        return this.yardsPerCarry;
    }

    //--- DATABASE MAPPING : yards_per_pass ( FLOAT ) 
    public void setYardsPerPass( Float yardsPerPass ) {
        this.yardsPerPass = yardsPerPass;
    }
    public Float getYardsPerPass() {
        return this.yardsPerPass;
    }

    //--- DATABASE MAPPING : yards_per_catch ( FLOAT ) 
    public void setYardsPerCatch( Float yardsPerCatch ) {
        this.yardsPerCatch = yardsPerCatch;
    }
    public Float getYardsPerCatch() {
        return this.yardsPerCatch;
    }

    //--- DATABASE MAPPING : touchdowns ( INT ) 
    public void setTouchdowns( Integer touchdowns ) {
        this.touchdowns = touchdowns;
    }
    public Integer getTouchdowns() {
        return this.touchdowns;
    }

    //--- DATABASE MAPPING : nbr_interceptions ( INT ) 
    public void setNbrInterceptions( Integer nbrInterceptions ) {
        this.nbrInterceptions = nbrInterceptions;
    }
    public Integer getNbrInterceptions() {
        return this.nbrInterceptions;
    }

    //--- DATABASE MAPPING : nbr_tackles ( FLOAT ) 
    public void setNbrTackles( Float nbrTackles ) {
        this.nbrTackles = nbrTackles;
    }
    public Float getNbrTackles() {
        return this.nbrTackles;
    }

    //--- DATABASE MAPPING : nbr_sacks ( FLOAT ) 
    public void setNbrSacks( Float nbrSacks ) {
        this.nbrSacks = nbrSacks;
    }
    public Float getNbrSacks() {
        return this.nbrSacks;
    }

    //--- DATABASE MAPPING : nbr_assists ( FLOAT ) 
    public void setNbrAssists( Float nbrAssists ) {
        this.nbrAssists = nbrAssists;
    }
    public Float getNbrAssists() {
        return this.nbrAssists;
    }

    //--- DATABASE MAPPING : opponent ( VARCHAR ) 
    public void setOpponent( String opponent ) {
        this.opponent = opponent;
    }
    public String getOpponent() {
        return this.opponent;
    }

    //--- DATABASE MAPPING : game_date ( DATE ) 
    public void setGameDate( Date gameDate ) {
        this.gameDate = gameDate;
    }
    public Date getGameDate() {
        return this.gameDate;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setPlayer( PlayerEntity player ) {
        this.player = player;
    }
    public PlayerEntity getPlayer() {
        return this.player;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        StringBuffer sb = new StringBuffer(); 
        return this.id.toString();
    } 

}