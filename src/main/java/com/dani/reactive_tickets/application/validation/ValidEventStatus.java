package com.dani.reactive_tickets.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventStatusValidator.class)
@Documented
public @interface ValidEventStatus {
    String message() default "Invalid event status. Valid values are: PUBLISHED, SOLD_OUT, CANCELLED, COMPLETED";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
