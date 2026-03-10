package com.ms.stock_control_api.exception.brand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class BrandExceptionDetails {
    private LocalDateTime timestamp;
    private int status;
    private String erro;
    private String message;
    private String path;
    private List<Object> fieldErros;
}
