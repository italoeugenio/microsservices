package com.ms.user.model.dtos;

import jakarta.validation.constraints.NotBlank;

public record ResendCodeRequestDTO(
        @NotBlank
        String email
) {
}
