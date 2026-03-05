package com.dani.reactive_tickets.application.port.in;

import com.dani.reactive_tickets.domain.model.Event;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GetEventUseCase {

    Flux<Event> getEvents(Pageable pageable);

    Mono<Event> getEventById(Integer id);

    Mono<Long> countEvents();

}
