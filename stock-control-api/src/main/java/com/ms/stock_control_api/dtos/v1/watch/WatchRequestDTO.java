package com.ms.stock_control_api.dtos.v1.watch;

import jakarta.validation.constraints.*;
import java.util.UUID;

public record WatchRequestDTO(

        @NotNull(message = "Brand is required")
        UUID brandId,

        @NotBlank(message = "Model is required")
        @Size(max = 100, message = "Model must have at most 100 characters")
        String model,

        @NotBlank(message = "Reference is required")
        @Size(max = 50, message = "Reference must have at most 50 characters")
        String reference,

        @NotBlank(message = "Movement type is required")
        String movementType,

        @NotBlank(message = "Case material is required")
        String boxMaterial,

        @NotBlank(message = "Glass type is required")
        String glassType,

        @Min(value = 0, message = "Water resistance cannot be negative")
        int waterResistanceM,

        @DecimalMin(value = "20.0", message = "Minimum diameter is 20mm")
        @DecimalMax(value = "60.0", message = "Maximum diameter is 60mm")
        float diameterMm,

        @DecimalMin(value = "0.0", message = "Lug to lug cannot be negative")
        float lugToLugMm,

        @DecimalMin(value = "0.0", message = "Thickness cannot be negative")
        float thicknessMm,

        @DecimalMin(value = "0.0", message = "Lug width cannot be negative")
        float lugWidthMm,

        @Min(value = 1, message = "Price must be greater than zero")
        long priceInCents,

        @NotBlank(message = "Image URL is required")
        @Size(max = 500, message = "URL must have at most 500 characters")
        String image

) {}