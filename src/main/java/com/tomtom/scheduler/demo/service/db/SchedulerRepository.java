package com.tomtom.scheduler.demo.service.db;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchedulerRepository extends CrudRepository<ScheduleEntity, UUID> {

}
