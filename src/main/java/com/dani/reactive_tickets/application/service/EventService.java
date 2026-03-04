package com.dani.reactive_tickets.application.service;

import com.dani.reactive_tickets.application.dto.EventRequest;
import com.dani.reactive_tickets.application.port.in.CreateEventUseCase;
import com.dani.reactive_tickets.application.port.out.EventRepository;
import com.dani.reactive_tickets.domain.model.Event;
import com.dani.reactive_tickets.domain.model.vo.EventStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EventService implements CreateEventUseCase{

    private final EventRepository eventRepository;

    @Override
    public Mono<Event> save(EventRequest eventRequest) {


        Event event = Event.builder()
                .name(eventRequest.getName())
                .description(eventRequest.getDescription())
                .eventDate(eventRequest.getEventDate())
                .site(eventRequest.getSite())
                .eventStatus(EventStatus.PUBLISHED)
                .imageUrl(eventRequest.getImageUrl())
                .build();

        event.validate();

        return eventRepository.save(event);
    }

}
