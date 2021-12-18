package com.tomtom.scheduler.demo.service.db;

import com.tomtom.scheduler.demo.api.Status;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "demo_schedules")
@Table(name = "demo_schedules")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ScheduleEntity {

  static TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");

  public ScheduleEntity() {
    this.id = UUID.randomUUID();
  }

  @Id
  @Column(name = "uuid", updatable = false, nullable = false)
  protected UUID id;

  @CreatedBy
  @Column(name = "created_by", updatable = false, nullable = true)
  protected String createdBy;

  @CreatedBy
  @LastModifiedBy
  @Column(name = "updated_by", updatable = true, nullable = true)
  protected String updatedBy;

  @Column(name = "created_date", updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar createdDate;

  @Column(name = "updated_date", updatable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar updatedDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", updatable = true)
  private Status status;

  @Column(name = "status_reason")
  private String statusReason;

  private String name;

  private String cron;

  @PrePersist
  public void ensurePrePersist() {
    if (this.createdDate == null) {
      this.createdDate = Calendar.getInstance(utcTimeZone);
    }
    if (this.updatedDate == null) {
      this.updatedDate = Calendar.getInstance(utcTimeZone);
    }
  }

  @PreUpdate
  public void ensurePreUpdate() {
    this.updatedDate = Calendar.getInstance(utcTimeZone);
  }

}
