package com.dani.reactive_tickets.infrastructure.adapter.out.persistence.imp;

import com.dani.reactive_tickets.application.port.out.EventRepository;
import com.dani.reactive_tickets.domain.exception.EventNotFoundException;
import com.dani.reactive_tickets.domain.model.Event;
import com.dani.reactive_tickets.infrastructure.adapter.out.persistence.entity.EventEntity;
import com.dani.reactive_tickets.infrastructure.adapter.out.persistence.repository.EventR2dbcRepository;
import com.dani.reactive_tickets.infrastructure.mapper.EventEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EventAdapter implements EventRepository {

    private final EventR2dbcRepository eventR2dbcRepository;
    private final EventEntityMapper eventEntityMapper;

    @Override
    public Mono<Event> save(Event event) {
        EventEntity eventEntity = eventEntityMapper.toEntity(event);
        return eventR2dbcRepository.save(eventEntity)
                .map(eventEntityMapper::toDomain);
    }

    @Override
    public Flux<Event> getEvents(Pageable pageable) {
        return eventR2dbcRepository.findAllBy(pageable)
                .map(eventEntityMapper::toDomain);
    }

    @Override
    public Mono<Event> getEventById(Integer id) {
        return eventR2dbcRepository.findById(id)
                .map(eventEntityMapper::toDomain)
                .switchIfEmpty(Mono.error(
                        new EventNotFoundException("Event not found"))
                );
    }

    @Override
    public Mono<Long> countEvents() {
        return eventR2dbcRepository.count();
    }
}
