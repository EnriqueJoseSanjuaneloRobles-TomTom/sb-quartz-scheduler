package com.tomtom.scheduler.demo.service;


import com.tomtom.scheduler.demo.api.Schedule;
import com.tomtom.scheduler.demo.service.db.ScheduleEntity;
import java.util.List;
import java.util.UUID;

public interface ScheduleService {

  Schedule create(Schedule schedule);

  Schedule getSchedule(UUID id);

  Schedule stopSchedule(UUID id);

  Schedule startSchedule(UUID id);

  List<Schedule> getSchedules();
}
