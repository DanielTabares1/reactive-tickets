package com.dani.reactive_tickets.application.port.in;

import com.dani.reactive_tickets.application.dto.EventRequest;
import com.dani.reactive_tickets.domain.model.Event;
import reactor.core.publisher.Mono;

public interface CreateEventUseCase {

    Mono<Event> save(EventRequest eventRequest);

}
