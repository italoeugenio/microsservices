package com.ms.stock_control_api.handler;

import com.ms.stock_control_api.exception.watch.WatchExceptionDetails;
import com.ms.stock_control_api.exception.watch.WatchExceptionNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class WatchHandler {

    @ExceptionHandler(WatchExceptionNotFound.class)
    public ResponseEntity<WatchExceptionDetails> handleWatchExceptionNotFound(WatchExceptionNotFound exception, HttpServletRequest request){
        ArrayList<Object> list = new ArrayList<>();

        return new ResponseEntity<>(
                WatchExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(404)
                        .message("Not found")
                        .erro("Watch not found with this id: " + exception.getMessage())
                        .path(request.getRequestURI())
                        .fieldErros(list)
                        .build(), HttpStatus.NOT_FOUND);
    }

}
