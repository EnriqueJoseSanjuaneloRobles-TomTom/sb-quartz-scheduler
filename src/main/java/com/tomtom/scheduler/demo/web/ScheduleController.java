package com.tomtom.scheduler.demo.web;

import com.tomtom.scheduler.demo.api.Schedule;
import com.tomtom.scheduler.demo.service.ScheduleService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/schedules")
public class ScheduleController {


  @Autowired
  ScheduleService scheduleService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Schedule>> list() {
    return ResponseEntity.ok().body(scheduleService.getSchedules());
  }

  @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Schedule> create(@PathVariable("id") UUID id) {
    Schedule schedule = scheduleService.getSchedule(id);
    if (schedule != null) {
      return ResponseEntity.ok().body(schedule);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Schedule> create(@RequestBody Schedule schedule) {
    return ResponseEntity.status(HttpStatus.CREATED).body(
        scheduleService.create(schedule)
    );
  }

  @PostMapping(value="/{id}/$stop", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Schedule> stop(@PathVariable("id") UUID id) {
    Schedule schedule = scheduleService.stopSchedule(id);
    if (schedule != null) {
      return ResponseEntity.ok().body(schedule);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping(value="/{id}/$start", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Schedule> start(@PathVariable("id") UUID id) {
    Schedule schedule = scheduleService.startSchedule(id);
    if (schedule != null) {
      return ResponseEntity.ok().body(schedule);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
