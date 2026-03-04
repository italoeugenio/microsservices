package com.ms.stock_control_api.exception.enums;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EnumExceptionInvalidInput extends RuntimeException {
    public EnumExceptionInvalidInput(String message) {
        super(message);
    }
}
