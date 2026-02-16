package com.ms.user.exception.tokenjwt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTokenOrExpired extends RuntimeException {
    public InvalidTokenOrExpired(String message) {
        super(message);
    }
}
