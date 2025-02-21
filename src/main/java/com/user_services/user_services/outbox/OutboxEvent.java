package com.user_services.user_services.outbox;

import com.user_services.user_services.enums.OutboxStatus;

public record OutboxEvent(
        Long id,
        String aggregate_type,
        long aggregate_id,
        String type,
        Object payload,
        OutboxStatus status
) {}