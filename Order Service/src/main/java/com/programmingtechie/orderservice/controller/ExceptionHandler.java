package com.programmingtechie.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> handleExceptions(IllegalAccessException exception)
    {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order Failed due to"+exception.getMessage());

    }
}
