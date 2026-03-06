package com.ms.user.handler;

import com.ms.user.exception.auth.ExpiredCodeException;
import com.ms.user.exception.auth.InvalidCodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthHandler {

    @ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<String> invalidCodeException(InvalidCodeException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredCodeException.class)
    public ResponseEntity<String> expiredCodeException(ExpiredCodeException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
