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

    private static final String PATH = "api/v1/event";

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    RouterFunction<ServerResponse> router(EventHandler handler) {
        return RouterFunctions.route()
                .POST(PATH, handler::createEvent)
                .build();
    }

}
