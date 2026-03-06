package com.dani.reactive_tickets.application.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    @NotBlank(message = "A name must be provided")
    @Size(min = 3, max = 200, message = "Name size must be between 3 and 200 characters")
    private String name;

    private String description;

    @NotNull(message = "A date must be provided")
    @Future(message = "Date must be in the future")
    private Instant eventDate;

    @NotNull(message = "A site must be provided")
    @Size(min = 3, max = 200, message = "Site size must be between 3 and 200 characters")
    private String site;

    private String imageUrl;
}
