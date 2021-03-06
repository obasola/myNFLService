/*
 * Created on 5 Jun 2016 ( Time 14:55:36 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kumasi.mynfl.domain.Draft;
import com.kumasi.mynfl.domain.jpa.DraftEntity;
import java.util.List;
import com.kumasi.mynfl.business.service.DraftService;
import com.kumasi.mynfl.business.service.mapping.DraftServiceMapper;
import com.kumasi.mynfl.persistence.PersistenceServiceProvider;
import com.kumasi.mynfl.persistence.services.DraftPersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of DraftService
 */
@Component
public class DraftServiceImpl implements DraftService {

	private DraftPersistence draftPersistence;

	@Resource
	private DraftServiceMapper draftServiceMapper;
	
	public DraftServiceImpl() {
		draftPersistence = PersistenceServiceProvider.getService(DraftPersistence.class);
	}
		
	@Override
	public Draft findById(Integer id) {
		DraftEntity entity = draftPersistence.load(id);
		return draftServiceMapper.mapDraftEntityToDraft(entity);
	}

	@Override
	public List<Draft> findAll() {
		List<DraftEntity> entities = draftPersistence.loadAll();
		List<Draft> beans = new ArrayList<Draft>();
		for(DraftEntity entity : entities) {
			beans.add(draftServiceMapper.mapDraftEntityToDraft(entity));
		}
		return beans;
	}

	@Override
	public Draft save(Draft draft) {
		return update(draft) ;
	}

	@Override
	public Draft create(Draft draft) {
		if(draftPersistence.load(draft.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		DraftEntity draftEntity = new DraftEntity();
		draftServiceMapper.mapDraftToDraftEntity(draft, draftEntity);
		DraftEntity draftEntitySaved = draftPersistence.save(draftEntity);
		return draftServiceMapper.mapDraftEntityToDraft(draftEntitySaved);
	}

	@Override
	public Draft update(Draft draft) {
		DraftEntity draftEntity = draftPersistence.load(draft.getId());
		draftServiceMapper.mapDraftToDraftEntity(draft, draftEntity);
		DraftEntity draftEntitySaved = draftPersistence.save(draftEntity);
		return draftServiceMapper.mapDraftEntityToDraft(draftEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		draftPersistence.delete(id);
	}

	public DraftPersistence getDraftPersistence() {
		return draftPersistence;
	}

	public void setDraftPersistence(DraftPersistence draftPersistence) {
		this.draftPersistence = draftPersistence;
	}

	public DraftServiceMapper getDraftServiceMapper() {
		return draftServiceMapper;
	}

	public void setDraftServiceMapper(DraftServiceMapper draftServiceMapper) {
		this.draftServiceMapper = draftServiceMapper;
	}

}
