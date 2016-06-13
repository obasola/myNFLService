/*
 * Created on 5 Jun 2016 ( Time 14:55:24 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
// This Bean has a basic Primary Key (not composite) 

package com.kumasi.mynfl.domain.jpa;

import java.io.Serializable;

//import javax.validation.constraints.* ;
//import org.hibernate.validator.constraints.* ;

import java.util.List;

import javax.persistence.*;

/**
 * Persistent class for entity stored in table "Draft_Team"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="Draft_Team", catalog="nfldb" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="DraftTeamEntity.countAll", query="SELECT COUNT(x) FROM DraftTeamEntity x" )
} )
public class DraftTeamEntity implements Serializable {

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
    @Column(name="team_code", length=3)
    private String     teamCode     ;

    @Column(name="team_name", length=45)
    private String     teamName     ;

	// "draftId" (column "Draft_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="draftTeam", targetEntity=DraftRoundEntity.class)
    private List<DraftRoundEntity> listOfDraftRound;

    @ManyToOne
    @JoinColumn(name="Draft_id", referencedColumnName="id")
    private DraftEntity draft       ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public DraftTeamEntity() {
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
    //--- DATABASE MAPPING : team_code ( VARCHAR ) 
    public void setTeamCode( String teamCode ) {
        this.teamCode = teamCode;
    }
    public String getTeamCode() {
        return this.teamCode;
    }

    //--- DATABASE MAPPING : team_name ( VARCHAR ) 
    public void setTeamName( String teamName ) {
        this.teamName = teamName;
    }
    public String getTeamName() {
        return this.teamName;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfDraftRound( List<DraftRoundEntity> listOfDraftRound ) {
        this.listOfDraftRound = listOfDraftRound;
    }
    public List<DraftRoundEntity> getListOfDraftRound() {
        return this.listOfDraftRound;
    }

    public void setDraft( DraftEntity draft ) {
        this.draft = draft;
    }
    public DraftEntity getDraft() {
        return this.draft;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        return this.teamName;
    } 

}