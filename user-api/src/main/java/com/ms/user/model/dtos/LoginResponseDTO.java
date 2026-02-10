package com.ms.user.model.dtos;

public record LoginResponseDTO(
        String email,
        String token,
        String refreshToken
) {

}
