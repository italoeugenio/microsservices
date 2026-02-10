package com.ms.user.exception.tokenjwt;

public class InvalidTokenOrExpired extends RuntimeException {
    public InvalidTokenOrExpired(String message) {
        super(message);
    }
}
