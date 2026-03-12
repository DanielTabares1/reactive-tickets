package com.dani.reactive_tickets.application.dto;

import com.dani.reactive_tickets.application.validation.ValidEventStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventStatusChangeRequest {
    @NotBlank(message = "A status must be provided")
    @ValidEventStatus
    private String status;
}
