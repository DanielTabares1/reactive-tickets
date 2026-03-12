package com.dani.reactive_tickets.application.mapper;

import com.dani.reactive_tickets.application.dto.EventRequest;
import com.dani.reactive_tickets.domain.model.Event;
import com.dani.reactive_tickets.domain.model.vo.EventStatus;
import org.springframework.stereotype.Component;

@Component
public class EventRequestMapper {

    public Event toDomain(EventRequest request) {
        return Event.builder()
                .name(request.getName())
                .description(request.getDescription())
                .eventDate(request.getEventDate())
                .site(request.getSite())
                .eventStatus(EventStatus.PUBLISHED)
                .imageUrl(request.getImageUrl())
                .build();
    }
}
