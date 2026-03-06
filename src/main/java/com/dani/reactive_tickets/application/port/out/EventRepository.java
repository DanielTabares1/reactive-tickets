package com.dani.reactive_tickets.application.port.out;

import com.dani.reactive_tickets.domain.model.Event;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EventRepository {

    Mono<Event> save(Event event);

    Flux<Event> getEvents(Pageable pageable);

    Mono<Event> getEventById(Integer id);

    Mono<Long> countEvents();

}
