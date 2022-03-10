package com.br.montesan.restaurant.handler;

import com.br.montesan.restaurant.exceptions.StatusChangeError;
import com.br.montesan.restaurant.exceptions.StatusChangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StatusChangeException.class)
    public ResponseEntity<?> handlerStatusChangeException(StatusChangeException statusChangeExceptionException){
        StatusChangeError statusChangeError = buildStatusChangeError(statusChangeExceptionException);

        return new ResponseEntity<>(statusChangeError, HttpStatus.PRECONDITION_FAILED);
    }

    private StatusChangeError buildStatusChangeError(StatusChangeException statusChangeException) {
        StatusChangeError statusChangeError = StatusChangeError.builder().build();
        statusChangeError.setDate(new Date().getTime());
        statusChangeError.setStatus(HttpStatus.PRECONDITION_FAILED.value());
        statusChangeError.setTitle("Invalid change status");
        statusChangeError.setDetail(statusChangeException.getMessage());
        statusChangeError.setMessage(statusChangeException.getClass().getName());
        return statusChangeError;
    }
}
