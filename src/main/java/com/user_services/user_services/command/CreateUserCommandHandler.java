package com.user_services.user_services.command;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateUserCommandHandler {
  private final JdbcTemplate jdbcTemplate;
  private final ApplicationEventPublisher applicationEventPublisher;
}
