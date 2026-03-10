package com.ms.stock_control_api.handler;

import com.ms.stock_control_api.exception.enums.EnumExceptionInvalidInput;
import com.ms.stock_control_api.exception.enums.EnumsExceptionDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class EnumsHandler {

    @ExceptionHandler(EnumExceptionInvalidInput.class)
    public ResponseEntity<EnumsExceptionDetails> handleEnumExceptionInvalidInput(EnumExceptionInvalidInput exception, HttpServletRequest request){
        ArrayList<Object> list = new ArrayList<>();
        return new ResponseEntity<>(
                EnumsExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(400)
                        .erro("Invalid Request")
                        .message(exception.getMessage())
                        .path(request.getRequestURI())
                        .fieldErros(list)
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
