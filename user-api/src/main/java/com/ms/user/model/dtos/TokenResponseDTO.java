package com.ms.user.model.dtos;

public record TokenResponseDTO(
        String accessToken,
        String refreshToken
)
{}