package com.ms.email_api.infra.resend;

import com.ms.email_api.adapters.EmailSenderGateway;
import com.ms.email_api.core.exceptions.EmailServiceException;
import com.ms.email_api.infra.config.ResendConfig;
import com.resend.core.exception.ResendException;
import com.resend.services.emails.model.CreateEmailOptions;
import com.resend.services.emails.model.CreateEmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ResendEmailSenderService implements EmailSenderGateway {

    @Autowired
    private ResendConfig resendConfig;

    @Value("${api.security.resend.email}")
    private String email;

    @Override
    public void sendEmail(String to, String subject, String body) {
        CreateEmailOptions params = CreateEmailOptions.builder()
                .from(email)
                .to(to)
                .subject(subject)
                .html(body)
                .build();
        try{
            CreateEmailResponse data = resendConfig.resendClient().emails().send(params);
        }catch (ResendException exception){
            throw new EmailServiceException("Failure while sending email", exception);
        }
    }
}
