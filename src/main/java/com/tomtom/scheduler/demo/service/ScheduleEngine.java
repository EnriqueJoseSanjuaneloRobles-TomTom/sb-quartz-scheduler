package com.tomtom.scheduler.demo.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import com.tomtom.scheduler.demo.api.Schedule;
import java.time.Instant;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ScheduleEngine {

  public static final String GROUP_ID = "JOB_PROCESS";
  public static final String PROCESS_ID_KEY = "JOB_PROCESS_ID";

  @Autowired
  private Scheduler scheduler;

  public void start(Schedule schedule) {
    Trigger logTrigger = createTrigger(schedule);
    JobDetail jobDetail = createJobDetail(schedule);
    try {
      scheduler.scheduleJobs(
          Stream.of(
              new SimpleImmutableEntry<>(jobDetail, Collections.singleton(logTrigger))
          )
              .collect(Collectors.toMap(SimpleImmutableEntry::getKey, SimpleImmutableEntry::getValue)),
          true
      );
    } catch (SchedulerException e) {
      log.error("Unable to schedule process {} for monitoring", schedule.getId());
    }
  }

  public void stop(Schedule schedule) {
    try {
      scheduler.unscheduleJob(TriggerKey.triggerKey(schedule.getId().toString(), GROUP_ID));
    } catch (SchedulerException e) {
      log.warn("Unable to unschedule job with id {}", schedule.getId());
    }
  }

  private Trigger createTrigger(Schedule schedule) {
    Instant now = Instant.now();
    return TriggerBuilder.newTrigger()
        .withIdentity(schedule.getId().toString(), GROUP_ID)
        .startAt(new Date(now.toEpochMilli()))
        .withSchedule(cronSchedule(schedule.getCron()))
        .build();
  }

  private JobDetail createJobDetail(Schedule schedule) {
    return JobBuilder.newJob()
        .ofType(ScheduleTask.class)
        .withIdentity(schedule.getId().toString(), GROUP_ID)
        .usingJobData(PROCESS_ID_KEY, schedule.getId().toString())
        .build();
  }
}
