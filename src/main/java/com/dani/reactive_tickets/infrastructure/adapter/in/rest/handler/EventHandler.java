package com.dani.reactive_tickets.infrastructure.adapter.in.rest.handler;

import com.dani.reactive_tickets.application.dto.EventRequest;
import com.dani.reactive_tickets.application.dto.EventStatusChangeRequest;
import com.dani.reactive_tickets.application.service.EventService;
import com.dani.reactive_tickets.application.validation.ObjectValidator;
import com.dani.reactive_tickets.domain.exception.InvalidEventIdException;
import com.dani.reactive_tickets.infrastructure.adapter.in.rest.dto.EventResponse;
import com.dani.reactive_tickets.infrastructure.adapter.in.rest.dto.PageInfo;
import com.dani.reactive_tickets.infrastructure.adapter.in.rest.dto.PageResponse;
import com.dani.reactive_tickets.infrastructure.adapter.in.rest.mapper.EventResponseMapper;
import com.dani.reactive_tickets.infrastructure.adapter.in.rest.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventHandler {

    private final EventService eventService;
    private final ObjectValidator objectValidator;
    private final EventResponseMapper eventResponseMapper;

    public Mono<ServerResponse> createEvent(ServerRequest request) {
        return request.bodyToMono(EventRequest.class)
                .flatMap(objectValidator::validate)
                .flatMap(eventService::save)
                .flatMap(event -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(eventResponseMapper.toResponse(event))
                );
    }

    public Mono<ServerResponse> getEvents(ServerRequest request) {

        Pageable pageable = PaginationUtil.extractPageable(request);

        Flux<EventResponse> events = eventService.getEvents(pageable)
                .map(eventResponseMapper::toResponse);

        Mono<Long> totalElements = eventService.countEvents();

        return events.collectList()
                .zipWith(totalElements)
                .map(tuple -> {
                    List<EventResponse> content = tuple.getT1();
                    Long total = tuple.getT2();

                    PageInfo pageInfo = PageInfo.of(
                            pageable.getPageNumber(),
                            pageable.getPageSize(),
                            total
                    );
                    return PageResponse.of(content, pageInfo);
                })
                .flatMap(pageResponse -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(pageResponse)
                );
    }

    public Mono<ServerResponse> getEventById(ServerRequest request) {
        return Mono.fromCallable(() -> Integer.parseInt(request.pathVariable("id")))
                .onErrorMap(NumberFormatException.class, e ->
                        new InvalidEventIdException("Invalid event ID format")
                )
                .flatMap(eventService::getEventById)
                .flatMap(event -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(eventResponseMapper.toResponse(event))
                );
    }

    public Mono<ServerResponse> updateEvent(ServerRequest request) {
        return Mono.fromCallable(() -> Integer.parseInt(request.pathVariable("id")))
                .onErrorMap(NumberFormatException.class, e ->
                        new InvalidEventIdException("Invalid event ID format")
                )
                .zipWith(request.bodyToMono(EventRequest.class)
                        .flatMap(objectValidator::validate)
                )
                .flatMap(tuple -> {
                    int id = tuple.getT1();
                    EventRequest eventRequest = tuple.getT2();
                    return eventService.update(id, eventRequest);
                })
                .flatMap(event -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(eventResponseMapper.toResponse(event))
                );
    }

    public Mono<ServerResponse> updateStatus(ServerRequest request) {
        return Mono.fromCallable(() -> Integer.parseInt(request.pathVariable("id")))
                .onErrorMap(NumberFormatException.class, e ->
                        new InvalidEventIdException("Invalid event ID format")
                )
                .zipWith(request.bodyToMono(EventStatusChangeRequest.class)
                        .flatMap(objectValidator::validate)
                )
                .flatMap(tuple -> {
                    int id = tuple.getT1();
                    EventStatusChangeRequest eventStatusChangeRequest = tuple.getT2();
                    return eventService.updateStatus(id, eventStatusChangeRequest.getStatus());
                })
                .flatMap(event -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(eventResponseMapper.toResponse(event))
                );
    }

}
