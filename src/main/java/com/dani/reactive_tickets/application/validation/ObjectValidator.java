package com.dani.reactive_tickets.application.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ObjectValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T object) {
        return Mono.fromCallable(() -> {
            Set<ConstraintViolation<T>> errors = validator.validate(object);
            if (!errors.isEmpty()) {
                String message = errors.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                throw new ValidatorException(message);
            }
            return object;
        }).subscribeOn(Schedulers.boundedElastic());
    }

}
