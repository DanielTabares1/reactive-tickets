package com.dani.reactive_tickets.domain.model;

import com.dani.reactive_tickets.domain.exception.InvalidEventStartDateException;
import com.dani.reactive_tickets.domain.exception.InvalidEventStatusException;
import com.dani.reactive_tickets.domain.model.vo.EventStatus;
import lombok.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    private Integer id;
    private String name;
    private String description;
    private Instant eventDate;
    private String site;
    private EventStatus eventStatus;
    private String imageUrl;
    private Instant createdAt;
    private Instant updatedAt;
    private Long version;

    public void validate() {
        validateInitialStatus();
        validateMinimumStartDate();
    }

    private void validateInitialStatus() {
        if (id == null && eventStatus != EventStatus.PUBLISHED) {
            throw new InvalidEventStatusException(
                    "New Events always must be in 'PUBLISHED' status"
            );
        }
    }

    private void validateMinimumStartDate() {
        Instant allowedMinimum = Instant.now()
                .plus(7, ChronoUnit.DAYS);

        if (eventDate.isBefore(allowedMinimum)) {
            throw new InvalidEventStartDateException(
                    "Event must start at least 7 days from today"
            );
        }
    }

    public void validateStatusChange(String newStatus) {
        validateStatusExists(newStatus);
        validateStatusTransition(newStatus);
    }

    private void validateStatusExists(String newStatus) {
        try {
            EventStatus.valueOf(newStatus);
        } catch (IllegalArgumentException e) {
            throw new InvalidEventStatusException("Invalid status: " + newStatus);
        }
    }

    private void validateStatusTransition(String newStatus) {
        EventStatus newStatusEnum = EventStatus.valueOf(newStatus);

        Map<EventStatus, List<EventStatus>> allowedTransitions = Map.of(
                EventStatus.PUBLISHED, List.of(EventStatus.SOLD_OUT, EventStatus.CANCELLED, EventStatus.COMPLETED),
                EventStatus.SOLD_OUT, List.of(EventStatus.CANCELLED, EventStatus.COMPLETED),
                EventStatus.CANCELLED, List.of(),
                EventStatus.COMPLETED, List.of()
        );

        if (!allowedTransitions.get(this.eventStatus).contains(newStatusEnum)) {
            throw new InvalidEventStatusException("Cannot transition from " + this.eventStatus + " to " + newStatusEnum);
        }
    }
}
