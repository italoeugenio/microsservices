package com.ms.email_api.consumer;

import com.ms.email_api.application.EmailSenderService;
import com.ms.email_api.dtos.EmailDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private EmailSenderService emailSenderService;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailDTO emailDTO) {
        emailSenderService.sendEmail(emailDTO.to(), emailDTO.subject(), emailDTO.body());
    }
}
