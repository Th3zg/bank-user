package com.user_services.user_services.enums;

import lombok.Getter;

@Getter
public enum RiskLevel {
  LOW("low"),
  MEDIUM("medium"),
  HIGH("high");

  private final String value;

  RiskLevel(String value) { this.value = value; }

}
