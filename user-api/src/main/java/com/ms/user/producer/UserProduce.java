package com.ms.user.producer;

import com.ms.user.model.dtos.EmailDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserProduce {
    private final RabbitTemplate rabbitTemplate;

    public UserProduce(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishEmailMessage(EmailDTO emailDTO){
        rabbitTemplate.convertAndSend("",routingKey, emailDTO);
    }
}
