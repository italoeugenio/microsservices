package com.ms.stock_control_api.exception.watch;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class WatchExceptionNotFound extends RuntimeException {

    public WatchExceptionNotFound(String message){
        super(message);
    }


}
