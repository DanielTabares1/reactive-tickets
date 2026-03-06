package com.dani.reactive_tickets.infrastructure.adapter.in.rest.router;

import com.dani.reactive_tickets.infrastructure.adapter.in.rest.handler.EventHandler;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class EventRouter {

    private static final String EVENT_PATH = "/api/v1/events";
    private static final String ID_PATH = "/{id}";
    private static final String STATUS_PATH = "/status";

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    RouterFunction<ServerResponse> router(EventHandler handler) {
        return RouterFunctions.route()
                .POST(EVENT_PATH, handler::createEvent)
                .GET(EVENT_PATH, handler::getEvents)
                .GET(EVENT_PATH + ID_PATH, handler::getEventById)
                .PUT(EVENT_PATH + ID_PATH, handler::updateEvent)
                .PATCH(EVENT_PATH + ID_PATH + STATUS_PATH, handler::updateStatus)
                .build();
    }

}
