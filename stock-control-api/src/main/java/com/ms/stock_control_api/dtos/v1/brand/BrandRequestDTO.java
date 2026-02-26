package com.ms.stock_control_api.dtos.v1.brand;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record BrandRequestDTO(
        @NotBlank(message = "must not be black")
        String name,

        @NotBlank(message = "must not be black")
        String country,

        @Min(value = 1, message = "must be a positive number")
        int founded
) {
}
