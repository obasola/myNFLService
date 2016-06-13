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
 * Persistent class for entity stored in table "Draft"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="Draft", catalog="nfldb" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="DraftEntity.countAll", query="SELECT COUNT(x) FROM DraftEntity x" )
} )
public class DraftEntity implements Serializable {

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
    @Column(name="year")
    private Integer    year         ;

	// "draftTypeId" (column "Draft_Type_id") is not defined by itself because used as FK in a link 


    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="draft", targetEntity=DraftTeamEntity.class)
    private List<DraftTeamEntity> listOfDraftTeam;

    @ManyToOne
    @JoinColumn(name="Draft_Type_id", referencedColumnName="id")
    private DraftTypeEntity draftType   ;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public DraftEntity() {
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
    //--- DATABASE MAPPING : year ( INT ) 
    public void setYear( Integer year ) {
        this.year = year;
    }
    public Integer getYear() {
        return this.year;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfDraftTeam( List<DraftTeamEntity> listOfDraftTeam ) {
        this.listOfDraftTeam = listOfDraftTeam;
    }
    public List<DraftTeamEntity> getListOfDraftTeam() {
        return this.listOfDraftTeam;
    }

    public void setDraftType( DraftTypeEntity draftType ) {
        this.draftType = draftType;
    }
    public DraftTypeEntity getDraftType() {
        return this.draftType;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
    	return this.year.toString();
    } 

}