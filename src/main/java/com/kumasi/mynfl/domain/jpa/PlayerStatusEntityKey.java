/*
 * Created on 5 Jun 2016 ( Time 14:55:25 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.domain.jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * Composite primary key for entity "PlayerStatusEntity" ( stored in table "Player_Status" )
 *
 * @author Telosys Tools Generator
 *
 */
 @Embeddable
public class PlayerStatusEntityKey implements Serializable {
    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY KEY ATTRIBUTES 
    //----------------------------------------------------------------------
    @Column(name="id", nullable=false)
    private Integer    id           ;
    
    @Column(name="Status_id", nullable=false)
    private Integer    statusId     ;
    

    //----------------------------------------------------------------------
    // CONSTRUCTORS
    //----------------------------------------------------------------------
    public PlayerStatusEntityKey() {
        super();
    }

    public PlayerStatusEntityKey( Integer id, Integer statusId ) {
        super();
        this.id = id ;
        this.statusId = statusId ;
    }
    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR KEY FIELDS
    //----------------------------------------------------------------------
    public void setId( Integer value ) {
        this.id = value;
    }
    public Integer getId() {
        return this.id;
    }

    public void setStatusId( Integer value ) {
        this.statusId = value;
    }
    public Integer getStatusId() {
        return this.statusId;
    }


    //----------------------------------------------------------------------
    // equals METHOD
    //----------------------------------------------------------------------
	public boolean equals(Object obj) { 
		if ( this == obj ) return true ; 
		if ( obj == null ) return false ;
		if ( this.getClass() != obj.getClass() ) return false ; 
		PlayerStatusEntityKey other = (PlayerStatusEntityKey) obj; 
		//--- Attribute id
		if ( id == null ) { 
			if ( other.id != null ) 
				return false ; 
		} else if ( ! id.equals(other.id) ) 
			return false ; 
		//--- Attribute statusId
		if ( statusId == null ) { 
			if ( other.statusId != null ) 
				return false ; 
		} else if ( ! statusId.equals(other.statusId) ) 
			return false ; 
		return true; 
	} 


    //----------------------------------------------------------------------
    // hashCode METHOD
    //----------------------------------------------------------------------
	public int hashCode() { 
		final int prime = 31; 
		int result = 1; 
		
		//--- Attribute id
		result = prime * result + ((id == null) ? 0 : id.hashCode() ) ; 
		//--- Attribute statusId
		result = prime * result + ((statusId == null) ? 0 : statusId.hashCode() ) ; 
		
		return result; 
	} 


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() {
		StringBuffer sb = new StringBuffer(); 
		sb.append(id); 
		sb.append("|"); 
		sb.append(statusId); 
        return sb.toString();
    }
}
