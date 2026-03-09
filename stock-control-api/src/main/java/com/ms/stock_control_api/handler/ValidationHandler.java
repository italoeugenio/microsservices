package com.ms.stock_control_api.handler;

import com.ms.stock_control_api.exception.brand.BrandExceptionDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BrandExceptionDetails>handleValidationExceptions(MethodArgumentNotValidException exception, HttpServletRequest request){
        Map<String, String> errors = new HashMap<>();
        ArrayList<Object> list = new ArrayList<>();

        for(FieldError fieldError: exception.getFieldErrors()){
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        list.add(errors);

        return new ResponseEntity<>(
                BrandExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(400)
                        .erro("Invalid request")
                        .message("Validation error")
                        .path(request.getRequestURI())
                        .fieldErros(list)
                        .build(), HttpStatus.BAD_REQUEST

        );
    }
}
