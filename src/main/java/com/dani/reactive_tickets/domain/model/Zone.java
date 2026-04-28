package com.dani.reactive_tickets.domain.model;

import com.dani.reactive_tickets.domain.exception.InvalidCapacityException;
import com.dani.reactive_tickets.domain.exception.InvalidPriceException;
import com.dani.reactive_tickets.domain.exception.NoAvailableCapacityException;
import com.dani.reactive_tickets.domain.model.vo.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class Zone {
    private final Integer id;
    private final Integer eventId;
    private final String name;
    private final String description;
    private final Integer capacity;
    private final Integer availableCapacity;
    private final Money price;
    private final Long version;

    public void validate() {
        validateCapacity();
        if (availableCapacity > capacity) {
            throw new InvalidCapacityException("Available capacity cannot exceed total capacity");
        }
    }

    public void validateCapacity() {
        if (capacity == null || capacity <= 0) {
            throw new InvalidCapacityException(String.format("Invalid zone capacity: [%d]. Capacity must be > 0.", capacity));
        }
    }

    public Zone reserveTicket(int quantity) {
        if (this.availableCapacity < quantity) {
            throw new NoAvailableCapacityException("No tickets available for zone: " + name);
        }
        return this.toBuilder()
                .availableCapacity(this.availableCapacity - quantity)
                .build();
    }

}


