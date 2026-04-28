package com.dani.reactive_tickets.domain.model.vo;

import com.dani.reactive_tickets.domain.exception.InvalidPriceException;

import java.math.BigDecimal;

public record Money(BigDecimal amount, String currency) {

    public Money {
        if (amount == null) throw new InvalidPriceException("Price must not be null");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPriceException(String.format("Invalid price: [%,.2f]. Price cannot be negative.", amount));
        }
        if (currency == null) {
            throw new InvalidPriceException("Currency must not be null");
        }
    }
}
