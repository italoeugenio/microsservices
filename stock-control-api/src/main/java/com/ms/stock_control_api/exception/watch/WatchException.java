package com.ms.stock_control_api.exception.watch;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WatchException extends RuntimeException {

    public WatchException(String message){
        super(message);
    }


}
