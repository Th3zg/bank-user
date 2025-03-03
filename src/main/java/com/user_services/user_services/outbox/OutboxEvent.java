package com.user_services.user_services.outbox;

import com.user_services.user_services.enums.AggregateType;
import com.user_services.user_services.enums.EventType;
import com.user_services.user_services.enums.OutboxStatus;

public record OutboxEvent(
        Long id,
        AggregateType aggregate_type,
        long aggregate_id,
        EventType type,
        Object payload,
        OutboxStatus status
) {}