package com.ms.email_api.consumer;

import com.ms.email_api.application.EmailSenderService;
import com.ms.email_api.dtos.EmailDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    private EmailSenderService emailSenderService;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailDTO emailDTO) {
        System.out.println("Mensagem recebida: " + emailDTO);
    }
}
