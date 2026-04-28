package com.dani.reactive_tickets.domain.exception;

public class InvalidPriceException extends DomainException {
    public InvalidPriceException(String message) {
        super(message);
    }
}
