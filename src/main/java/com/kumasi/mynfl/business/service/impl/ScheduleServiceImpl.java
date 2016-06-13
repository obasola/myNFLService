/*
 * Created on 5 Jun 2016 ( Time 14:55:37 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.kumasi.mynfl.business.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.kumasi.mynfl.domain.Schedule;
import com.kumasi.mynfl.domain.jpa.ScheduleEntity;
import com.kumasi.mynfl.business.service.ScheduleService;
import com.kumasi.mynfl.business.service.mapping.ScheduleServiceMapper;
import com.kumasi.mynfl.persistence.PersistenceServiceProvider;
import com.kumasi.mynfl.persistence.services.SchedulePersistence;
import org.springframework.stereotype.Component;

/**
 * Implementation of ScheduleService
 */
@Component
public class ScheduleServiceImpl implements ScheduleService {

	private SchedulePersistence schedulePersistence;

	@Resource
	private ScheduleServiceMapper scheduleServiceMapper;
	
	public ScheduleServiceImpl() {
		schedulePersistence = PersistenceServiceProvider.getService(SchedulePersistence.class);
	}
		
	@Override
	public Schedule findById(Integer id) {
		ScheduleEntity entity = schedulePersistence.load(id);
		return scheduleServiceMapper.mapScheduleEntityToSchedule(entity);
	}

	@Override
	public List<Schedule> findAll() {
		List<ScheduleEntity> entities = schedulePersistence.loadAll();
		List<Schedule> beans = new ArrayList<Schedule>();
		for(ScheduleEntity entity : entities) {
			beans.add(scheduleServiceMapper.mapScheduleEntityToSchedule(entity));
		}
		return beans;
	}

	@Override
	public Schedule save(Schedule schedule) {
		return update(schedule) ;
	}

	@Override
	public Schedule create(Schedule schedule) {
		if(schedulePersistence.load(schedule.getId()) != null) {
			throw new IllegalStateException("already.exists");
		}
		ScheduleEntity scheduleEntity = new ScheduleEntity();
		scheduleServiceMapper.mapScheduleToScheduleEntity(schedule, scheduleEntity);
		ScheduleEntity scheduleEntitySaved = schedulePersistence.save(scheduleEntity);
		return scheduleServiceMapper.mapScheduleEntityToSchedule(scheduleEntitySaved);
	}

	@Override
	public Schedule update(Schedule schedule) {
		ScheduleEntity scheduleEntity = schedulePersistence.load(schedule.getId());
		scheduleServiceMapper.mapScheduleToScheduleEntity(schedule, scheduleEntity);
		ScheduleEntity scheduleEntitySaved = schedulePersistence.save(scheduleEntity);
		return scheduleServiceMapper.mapScheduleEntityToSchedule(scheduleEntitySaved);
	}

	@Override
	public void delete(Integer id) {
		schedulePersistence.delete(id);
	}

	public SchedulePersistence getSchedulePersistence() {
		return schedulePersistence;
	}

	public void setSchedulePersistence(SchedulePersistence schedulePersistence) {
		this.schedulePersistence = schedulePersistence;
	}

	public ScheduleServiceMapper getScheduleServiceMapper() {
		return scheduleServiceMapper;
	}

	public void setScheduleServiceMapper(ScheduleServiceMapper scheduleServiceMapper) {
		this.scheduleServiceMapper = scheduleServiceMapper;
	}

}
