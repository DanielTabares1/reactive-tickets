package com.dani.reactive_tickets.application.service;

import com.dani.reactive_tickets.application.dto.EventRequest;
import com.dani.reactive_tickets.application.mapper.EventRequestMapper;
import com.dani.reactive_tickets.application.port.in.CreateEventUseCase;
import com.dani.reactive_tickets.application.port.in.GetEventUseCase;
import com.dani.reactive_tickets.application.port.in.UpdateEventUseCase;
import com.dani.reactive_tickets.application.port.out.EventRepository;
import com.dani.reactive_tickets.domain.model.Event;
import com.dani.reactive_tickets.domain.model.vo.EventStatus;
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
public class EventService implements CreateEventUseCase, GetEventUseCase, UpdateEventUseCase {

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
                .doOnNext(event -> log.debug("Event found: {}", event.getName()))
                .doOnError(error -> log.error("Error fetching event with id: {}", id, error));
    }

    @Override
    public Mono<Long> countEvents() {
        return eventRepository.countEvents();
    }


    @Transactional
    @Override
    public Mono<Event> update(int id, EventRequest request) {
        return eventRepository.getEventById(id)
                .map(event ->
                        event.withUpdatedInfo(
                                request.getName(),
                                request.getDescription(),
                                request.getEventDate(),
                                request.getSite(),
                                request.getImageUrl()
                        )
                )
                .doOnNext(Event::validate)
                .flatMap(eventRepository::save);
    }

    @Transactional
    @Override
    public Mono<Event> updateStatus(int id, String status) {
        return eventRepository.getEventById(id)
                .doOnNext(event -> event.validateStatusChange(status))
                .map(event ->
                        event.withStatus(EventStatus.valueOf(status))
                )
                .flatMap(eventRepository::save);
    }
}
