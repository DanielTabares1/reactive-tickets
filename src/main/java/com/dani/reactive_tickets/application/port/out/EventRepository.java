package com.dani.reactive_tickets.application.port.out;

import com.dani.reactive_tickets.domain.model.Event;
import reactor.core.publisher.Mono;

public interface EventRepository {

    Mono<Event> save(Event event);

}
