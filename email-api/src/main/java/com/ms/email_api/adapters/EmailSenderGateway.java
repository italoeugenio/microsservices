package com.ms.email_api.adapters;

public interface EmailSenderGateway {
    void sendEmail(String to, String subject, String body);
}
