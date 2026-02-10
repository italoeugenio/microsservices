package com.ms.user.exception.auth;

public class ExpiredCodeException extends RuntimeException {
    public ExpiredCodeException(String message) {
        super(message);
    }
}
