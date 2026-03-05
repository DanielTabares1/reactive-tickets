package com.dani.reactive_tickets.domain.exception;

public class InvalidEventIdException extends DomainException {
    public InvalidEventIdException(String message) {
        super(message);
    }
}
