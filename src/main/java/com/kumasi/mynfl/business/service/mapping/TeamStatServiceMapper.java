/*
 * Created on 5 Jun 2016 ( Time 14:55:37 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.kumasi.mynfl.domain.TeamStat;
import com.kumasi.mynfl.domain.jpa.TeamStatEntity;
import com.kumasi.mynfl.domain.jpa.TeamEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class TeamStatServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public TeamStatServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'TeamStatEntity' to 'TeamStat'
	 * @param teamStatEntity
	 */
	public TeamStat mapTeamStatEntityToTeamStat(TeamStatEntity teamStatEntity) {
		if(teamStatEntity == null) {
			return null;
		}

		//--- Generic mapping 
		TeamStat teamStat = map(teamStatEntity, TeamStat.class);

		//--- Link mapping ( link to Team )
		if(teamStatEntity.getTeam() != null) {
			teamStat.setTeamId(teamStatEntity.getTeam().getId());
		}
		return teamStat;
	}
	
	/**
	 * Mapping from 'TeamStat' to 'TeamStatEntity'
	 * @param teamStat
	 * @param teamStatEntity
	 */
	public void mapTeamStatToTeamStatEntity(TeamStat teamStat, TeamStatEntity teamStatEntity) {
		if(teamStat == null) {
			return;
		}

		//--- Generic mapping 
		map(teamStat, teamStatEntity);

		//--- Link mapping ( link : teamStat )
		if( hasLinkToTeam(teamStat) ) {
			TeamEntity team1 = new TeamEntity();
			team1.setId( teamStat.getTeamId() );
			teamStatEntity.setTeam( team1 );
		} else {
			teamStatEntity.setTeam( null );
		}

	}
	
	/**
	 * Verify that Team id is valid.
	 * @param Team Team
	 * @return boolean
	 */
	private boolean hasLinkToTeam(TeamStat teamStat) {
		if(teamStat.getTeamId() != null) {
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}