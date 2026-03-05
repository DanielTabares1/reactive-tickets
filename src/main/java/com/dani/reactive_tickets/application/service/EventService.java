package com.dani.reactive_tickets.application.service;

import com.dani.reactive_tickets.application.dto.EventRequest;
import com.dani.reactive_tickets.application.mapper.EventRequestMapper;
import com.dani.reactive_tickets.application.port.in.CreateEventUseCase;
import com.dani.reactive_tickets.application.port.in.GetEventUseCase;
import com.dani.reactive_tickets.application.port.out.EventRepository;
import com.dani.reactive_tickets.domain.exception.EventNotFoundException;
import com.dani.reactive_tickets.domain.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService implements CreateEventUseCase, GetEventUseCase {

    private final EventRepository eventRepository;
    private final EventRequestMapper eventRequestMapper;

    @Transactional
    @Override
    public Mono<Event> save(EventRequest eventRequest) {
        return Mono.just(eventRequest)
                .map(eventRequestMapper::toDomain)
                .doOnNext(Event::validate)
                .flatMap(eventRepository::save);
    }

    @Override
    public Flux<Event> getEvents(Pageable pageable) {
        return eventRepository.getEvents(pageable)
                .doOnSubscribe(s ->
                        log.debug("Fetching events - page: {}, size: {}",
                                pageable.getPageNumber(), pageable.getPageSize()))
                .doOnComplete(() -> log.debug("Events fetched successfully"))
                .doOnError(error -> log.error("Error fetching events", error));
    }

    @Override
    public Mono<Event> getEventById(Integer id) {
        return eventRepository.getEventById(id)
                .doOnSubscribe(s -> log.debug("Fetching event with id: {}", id))
                .doOnSuccess(event -> log.debug("Event found: {}", event.getName()))
                .doOnError(error -> log.error("Error fetching event with id: {}", id, error));
    }

    @Override
    public Mono<Long> countEvents() {
        return eventRepository.countEvents();
    }
}
