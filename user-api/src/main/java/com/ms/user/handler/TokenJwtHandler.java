package com.ms.user.handler;

import com.ms.user.exception.auth.ExpiredCodeException;
import com.ms.user.exception.auth.InvalidCodeException;
import com.ms.user.exception.tokenjwt.InvalidTokenOrExpired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TokenJwtHandler {

    @ExceptionHandler(InvalidTokenOrExpired.class)
    public ResponseEntity<String> invalidTokenOrExpired(InvalidTokenOrExpired exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
