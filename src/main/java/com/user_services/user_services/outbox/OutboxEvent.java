package com.user_services.user_services.outbox;

import java.time.LocalDateTime;

public record OutboxEvent(
        Long id,
        String aggregate_type,
        long aggregate_id,
        Object payload,
        OutboxEvent status,
        LocalDateTime createdAt
) {}