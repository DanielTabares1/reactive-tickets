package com.dani.reactive_tickets.domain.exception;

public class InvalidEventStatusException extends DomainException {

    public InvalidEventStatusException(String message) {
        super(message);
    }

}