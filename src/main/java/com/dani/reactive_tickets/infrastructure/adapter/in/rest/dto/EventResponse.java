package com.dani.reactive_tickets.infrastructure.adapter.in.rest.dto;

import java.time.Instant;

public record EventResponse(
        Integer id,
        String name,
        String description,
        Instant eventDate,
        String site,
        String status,
        String imageUrl,
        Instant createdAt
) {}