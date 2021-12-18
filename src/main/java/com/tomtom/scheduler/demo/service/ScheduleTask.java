package com.tomtom.scheduler.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

@Slf4j
@DisallowConcurrentExecution // avoids overlapping executions of the same task (job)
@PersistJobDataAfterExecution
public class ScheduleTask implements Job {

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    JobDetail jobDetail = context.getJobDetail();

    Object counterObj = jobDetail.getJobDataMap().get("counter");
    int counter = 1;
    if (counterObj != null) {
      counter = (Integer) counterObj + 1;
    }
    jobDetail.getJobDataMap().put("counter", counter);
    log.info("scheduled task called {} - count {}",jobDetail.getKey(), counter);
  }
}
