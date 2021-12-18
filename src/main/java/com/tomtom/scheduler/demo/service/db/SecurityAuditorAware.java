package com.tomtom.scheduler.demo.service.db;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class SecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.of("someone@tomtom.com");
  }
}
