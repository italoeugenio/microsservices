package com.ms.stock_control_api.handler;

import com.ms.stock_control_api.exception.brand.BrandExceptionDetails;
import com.ms.stock_control_api.exception.brand.BrandExceptionNotFound;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class BrandHandler {

    @ExceptionHandler(BrandExceptionNotFound.class)
    public ResponseEntity<BrandExceptionDetails> handlerBrandNotFound(BrandExceptionNotFound exception, HttpServletRequest request){
        ArrayList<Object> list = new ArrayList<>();
        return new ResponseEntity<>(
                BrandExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(404)
                        .erro("Brand not found")
                        .message("Brand not found: " + exception.getMessage())
                        .path(request.getRequestURI())
                        .fieldErros(list)
                        .build(), HttpStatus.NOT_FOUND

        );
    }
}
