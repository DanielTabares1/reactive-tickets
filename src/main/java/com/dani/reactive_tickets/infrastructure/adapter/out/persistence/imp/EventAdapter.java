package com.dani.reactive_tickets.infrastructure.adapter.out.persistence.imp;

import com.dani.reactive_tickets.application.port.out.EventRepository;
import com.dani.reactive_tickets.domain.model.Event;
import com.dani.reactive_tickets.infrastructure.adapter.out.persistence.entity.EventEntity;
import com.dani.reactive_tickets.infrastructure.adapter.out.persistence.repository.EventR2dbcRepository;
import com.dani.reactive_tickets.infrastructure.mapper.EventEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class EventAdapter implements EventRepository {

    private final EventR2dbcRepository eventR2dbcRepository;
    private final EventEntityMapper eventEntityMapper;

    @Override
    public Mono<Event> save(Event event) {

        Instant now = Instant.now();

        EventEntity eventEntity = EventEntity.builder()
                .name(event.getName())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .site(event.getSite())
                .status(event.getEventStatus().name())
                .imageUrl(event.getImageUrl())
                .createdAt(now)
                .updatedAt(now)
                .build();

        return  eventR2dbcRepository.save(eventEntity)
                .map(eventEntityMapper::toDomain);
    }
}
