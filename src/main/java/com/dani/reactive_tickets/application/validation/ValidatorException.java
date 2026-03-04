package com.dani.reactive_tickets.application.validation;

import org.springframework.http.HttpStatus;

public class ValidatorException extends RuntimeException{
    private HttpStatus httpStatus;

    public ValidatorException(HttpStatus httpStatus, String message){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus(){
        return this.httpStatus;
    }
}
