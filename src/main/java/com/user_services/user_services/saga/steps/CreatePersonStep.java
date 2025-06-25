package com.user_services.user_services.saga.steps;

import com.user_services.user_services.saga.CreateClientSagaCommand;
import com.user_services.user_services.saga.SagaStep;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class CreatePersonStep implements SagaStep {
  @Override
  public Void execute(CreateClientSagaCommand command) {
    return null;
  }

  @Override
  public Void compensate(CreateClientSagaCommand command) {
    return null;
  }
}
