package com.tomtom.scheduler.demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Schedule {

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private UUID id;

  private String name;

  private String statusReason;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Status status = Status.UNKNOWN;

  private String cron;
}
