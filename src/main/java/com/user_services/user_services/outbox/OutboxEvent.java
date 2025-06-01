package com.user_services.user_services.outbox;

import com.user_services.user_services.enums.AggregateType;
import com.user_services.user_services.enums.EventType;
import com.user_services.user_services.enums.OutboxStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record OutboxEvent(
        Long id,
        AggregateType aggregate_type,
        long aggregate_id,
        EventType type,
        Object payload, // data (JSONB)
        OutboxStatus status,
        UUID eventId,
        int attempts,
        LocalDateTime processed_at,
        String last_error
) {}