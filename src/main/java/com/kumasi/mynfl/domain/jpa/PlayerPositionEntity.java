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
 * Persistent class for entity stored in table "Player_Position"
 *
 * @author Telosys Tools Generator
 *
 */

@Entity
@Table(name="Player_Position", catalog="nfldb" )
// Define named queries here
@NamedQueries ( {
  @NamedQuery ( name="PlayerPositionEntity.countAll", query="SELECT COUNT(x) FROM PlayerPositionEntity x" )
} )
public class PlayerPositionEntity implements Serializable {

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
    @Column(name="position_name", length=45)
    private String     positionName ;

    @Column(name="description", length=500)
    private String     description  ;



    //----------------------------------------------------------------------
    // ENTITY LINKS ( RELATIONSHIP )
    //----------------------------------------------------------------------
    @OneToMany(mappedBy="playerPosition", targetEntity=PlayerEntity.class)
    private List<PlayerEntity> listOfPlayer;


    //----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    //----------------------------------------------------------------------
    public PlayerPositionEntity() {
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
    //--- DATABASE MAPPING : position_name ( VARCHAR ) 
    public void setPositionName( String positionName ) {
        this.positionName = positionName;
    }
    public String getPositionName() {
        return this.positionName;
    }

    //--- DATABASE MAPPING : description ( VARCHAR ) 
    public void setDescription( String description ) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }


    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR LINKS
    //----------------------------------------------------------------------
    public void setListOfPlayer( List<PlayerEntity> listOfPlayer ) {
        this.listOfPlayer = listOfPlayer;
    }
    public List<PlayerEntity> getListOfPlayer() {
        return this.listOfPlayer;
    }


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() { 
        return this.positionName;
    } 

}