package com.br.montesan.restaurant.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class StatusChangeException extends RuntimeException {

    public StatusChangeException(String message){
        super(message);
    }
}
