package ru.rodionov.spring.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.rodionov.spring.exceptions.ClientNotFoundException;
import ru.rodionov.spring.exceptions.TransactionNotFoundException;
import ru.rodionov.spring.exceptions.UserNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = {
            ClientNotFoundException.class,
            TransactionNotFoundException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<String> handlerIllegalArgumentException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
