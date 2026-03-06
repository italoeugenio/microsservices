package com.ms.email_api.core;

public interface EmailSenderUseCase {
    void sendEmail(String to, String subject, String body);
}
