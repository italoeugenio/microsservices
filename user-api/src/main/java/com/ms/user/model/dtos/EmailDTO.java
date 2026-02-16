package com.ms.user.model.dtos;

public record EmailDTO(
        String to,
        String subject,
        String body
) {

}
