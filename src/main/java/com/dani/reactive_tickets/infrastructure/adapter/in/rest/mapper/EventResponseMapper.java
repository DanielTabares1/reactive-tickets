package com.dani.reactive_tickets.infrastructure.adapter.in.rest.mapper;

import com.dani.reactive_tickets.domain.model.Event;
import com.dani.reactive_tickets.infrastructure.adapter.in.rest.dto.EventResponse;
import org.springframework.stereotype.Component;

@Component
public class EventResponseMapper {

    public EventResponse toResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getEventDate(),
                event.getSite(),
                event.getEventStatus().name(),
                event.getImageUrl(),
                event.getCreatedAt()
        );
    }
}