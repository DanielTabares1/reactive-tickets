package com.dani.reactive_tickets.infrastructure.config;

import com.dani.reactive_tickets.application.validation.ValidatorException;
import com.dani.reactive_tickets.domain.exception.*;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.webflux.autoconfigure.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.webflux.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Component
public class GlobalExceptionHandler extends AbstractErrorWebExceptionHandler {

    public GlobalExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer codecConfigurer){
        super(errorAttributes, resources, applicationContext);
        this.setMessageReaders(codecConfigurer.getReaders());
        this.setMessageWriters(codecConfigurer.getWriters());
    }

    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS = Map.of(
            ValidatorException.class, HttpStatus.BAD_REQUEST,
            InvalidEventStartDateException.class, HttpStatus.BAD_REQUEST,
            InvalidEventIdException.class, HttpStatus.BAD_REQUEST,
            InvalidEventStatusException.class, HttpStatus.BAD_REQUEST,
            EventNotFoundException.class, HttpStatus.NOT_FOUND,
            DomainException.class, HttpStatus.BAD_REQUEST
    );

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::customErrorResponse);
    }

    private Mono<ServerResponse> customErrorResponse(ServerRequest serverRequest) {
        Throwable error = getError(serverRequest);

        Map<String, Object> errorMap = this.getErrorAttributes(
                serverRequest,
                ErrorAttributeOptions.defaults()
        );

        // Buscar el status en el Map usando la clase de la excepción
        HttpStatus status = EXCEPTION_STATUS.entrySet().stream()
                .filter(entry -> entry.getKey().isInstance(error))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseGet(() -> {
                    Integer statusCode = (Integer) Optional.ofNullable(errorMap.get("status"))
                            .orElse(500);
                    return HttpStatus.valueOf(statusCode);
                });

        // Agregar mensaje personalizado para excepciones conocidas
        if (EXCEPTION_STATUS.containsKey(error.getClass())) {
            errorMap.put("message", error.getMessage());
        }

        errorMap.put("status", status.value());
        errorMap.put("error", status.getReasonPhrase());

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorMap));
    }
}
