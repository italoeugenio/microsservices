package com.ms.email_api.dtos;

public record EmailDTO(
        String to,
        String subject,
        String body
) {

}
