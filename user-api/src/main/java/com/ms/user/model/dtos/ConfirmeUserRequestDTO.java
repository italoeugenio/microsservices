package com.ms.user.model.dtos;

import jakarta.validation.constraints.NotBlank;

public record ConfirmeUserRequestDTO(
        @NotBlank
        String email,

        @NotBlank
        String code
) {
}
