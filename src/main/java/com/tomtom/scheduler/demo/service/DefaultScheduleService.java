package com.tomtom.scheduler.demo.service;

import com.tomtom.scheduler.demo.api.Schedule;
import com.tomtom.scheduler.demo.api.Status;
import com.tomtom.scheduler.demo.service.db.ScheduleEntity;
import com.tomtom.scheduler.demo.service.db.SchedulerRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultScheduleService implements ScheduleService {

  @Autowired
  ScheduleEngine scheduleEngine;

  @Autowired
  SchedulerRepository schedulerRepository;

  @Override
  @Transactional
  public Schedule create(Schedule schedule) {

    ScheduleEntity entity = toEntity(schedule);
    entity = schedulerRepository.save(entity);
    startSchedule(entity.getId());

    return schedule;
  }

  public Schedule getSchedule(UUID id) {
    Optional<ScheduleEntity> result = schedulerRepository.findById(id);
    return result.map(this:: toResource).orElse(null);
  }

  @Override
  @Transactional
  public Schedule stopSchedule(UUID id) {
    return changeStatus(id, Status.DISABLED);
  }

  @Override
  public List<Schedule> getSchedules() {
    Iterable<ScheduleEntity> entities = schedulerRepository.findAll();

    List<Schedule> result = StreamSupport.stream(entities.spliterator(), false)
        .map(this::toResource)
        .collect(Collectors.toList());

    return result;

  }

  public Schedule startSchedule(UUID id) {
    return changeStatus(id, Status.ENABLED);
  }

  private Schedule changeStatus(UUID id, Status status){
    Schedule schedule = getSchedule(id);
    if (schedule == null) {
      log.info("Schedule not found {}", id);
      return null;
    }

    if (schedule.getStatus().equals(status)){
      log.info("Schedule status is the same {}", id);
      return schedule;
    }

    ScheduleEntity entity = schedulerRepository.findById(id).get();
    entity.setStatus(status);
    schedulerRepository.save(entity);

    switch (status){
      case ENABLED:
        scheduleEngine.start(schedule);
        break;
      case DISABLED:
        scheduleEngine.stop(schedule);
        break;
      default:
        break;
    }

    return getSchedule(entity.getId());
  }

  private ScheduleEntity toEntity(Schedule schedule) {
    ScheduleEntity entity = new ScheduleEntity();
    entity.setCron(schedule.getCron());
    entity.setName(schedule.getName());
    entity.setStatus(schedule.getStatus());
    return entity;
  }
  private Schedule toResource(ScheduleEntity entity){
    Schedule resource = new Schedule();
    resource.setId(entity.getId());
    resource.setCron(entity.getCron());
    resource.setName(entity.getName());
    resource.setStatus(entity.getStatus());
    return resource;
  }
}
