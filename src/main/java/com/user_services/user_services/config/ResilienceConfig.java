package com.user_services.user_services.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.Retry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResilienceConfig {

  @Bean
  public Retry kafkaRetry() {
    return Retry.ofDefaults("kafkaSenderRetry");
  }

  @Bean
  public CircuitBreaker kafkaCircuitBreaker() {
    return CircuitBreaker.ofDefaults("kafkaSenderBreaker");
  }
}

