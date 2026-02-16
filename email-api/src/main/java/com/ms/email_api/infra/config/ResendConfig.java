package com.ms.email_api.infra.config;

import com.resend.Resend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResendConfig {

    @Value("${api.security.resend.token}")
    private String secret;

    @Bean
    public Resend resendClient(){
        return new Resend(secret);
    }
}
