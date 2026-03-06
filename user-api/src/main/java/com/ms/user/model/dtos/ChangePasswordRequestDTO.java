package com.ms.user.model.dtos;

public record ChangePasswordRequestDTO(
        String currentPassword,
        String newPassword
) {
}
