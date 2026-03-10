package com.ms.stock_control_api.exception.watch;

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
public class WatchExceptionDetails {
    private LocalDateTime timestamp;
    private int status;
    private String erro;
    private String message;
    private String path;
    private List<Object> fieldErros;
}
