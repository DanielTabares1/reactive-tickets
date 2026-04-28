package com.dani.reactive_tickets.domain.exception;

public class NoAvailableCapacityException extends DomainException {
    public NoAvailableCapacityException(String message) {
        super(message);
    }
}
