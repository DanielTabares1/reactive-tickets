package com.dani.reactive_tickets.application.validation;

import com.dani.reactive_tickets.domain.model.vo.EventStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventStatusValidator implements ConstraintValidator<ValidEventStatus, String> {

    @Override
    public void initialize(ValidEventStatus constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null || value.isBlank()) {
            return true;
        }

        try {
            EventStatus.valueOf(value);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }
}
