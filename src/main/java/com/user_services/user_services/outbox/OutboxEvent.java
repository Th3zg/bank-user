package com.user_services.user_services.outbox;

import java.time.LocalDateTime;

public record OutboxEvent(
        Long id,
        String eventType,
        Object payload,
        LocalDateTime createdAt,
        Boolean processed
) {}