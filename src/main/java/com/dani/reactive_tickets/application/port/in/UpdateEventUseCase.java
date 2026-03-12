package com.dani.reactive_tickets.application.port.in;

import com.dani.reactive_tickets.application.dto.EventRequest;
import com.dani.reactive_tickets.domain.model.Event;
import reactor.core.publisher.Mono;

public interface UpdateEventUseCase {
    Mono<Event> update(int id, EventRequest request);
    Mono<Event> updateStatus(int id, String status);
}
