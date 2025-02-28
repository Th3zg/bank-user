package com.user_services.user_services.model.entity;

import com.user_services.user_services.enums.OutboxStatus;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public final class Outbox {
  private final Long id;
  private final String aggregate_type;
  private final long aggregate_id;
  private final String type;
  private final Object payload;
  private final OutboxStatus status;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public Outbox(Builder builder) {
    this.id = builder.id;
    this.aggregate_type = builder.aggregate_type;
    this.aggregate_id = builder.aggregate_id;
    this.type = builder.type;
    this.payload = builder.payload;
    this.status = builder.status;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
  }

  public static class Builder {
    private Long id;
    private String aggregate_type;
    private long aggregate_id;
    private String type;
    private Object payload;
    private OutboxStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Builder setId(Long id) { this.id = id; return this; }
    public Builder setAggregateType(String aggregate_type) { this.aggregate_type = aggregate_type; return this; }
    public Builder setAggregateId(long aggregate_id) { this.aggregate_id = aggregate_id; return this; }
    public Builder setType(String type) { this.type = type; return this; }
    public Builder setPayload(Object payload) { this.payload = payload; return this; }
    public Builder setStatus(OutboxStatus status) { this.status = status; return this; }
    public Builder setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
    public Builder setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
    public Outbox build() {
      return new Outbox(this);
    }
  }
}