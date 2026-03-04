package com.dani.reactive_tickets.infrastructure.adapter.in.rest.handler;

import com.dani.reactive_tickets.application.dto.EventRequest;
import com.dani.reactive_tickets.application.service.EventService;
import com.dani.reactive_tickets.application.validation.ObjectValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EventHandler {

    private final EventService eventService;
    private final ObjectValidator objectValidator;

    public Mono<ServerResponse> createEvent(ServerRequest request) {
        Mono<EventRequest> eventDto = request.bodyToMono(EventRequest.class).doOnNext(objectValidator::validate);
        return eventDto.flatMap(eventRequest -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(eventService.save(eventRequest), EventRequest.class));
    }

}
