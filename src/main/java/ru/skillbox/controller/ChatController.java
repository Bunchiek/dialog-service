package ru.skillbox.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.skillbox.configuration.RabbitMQConfig;
import ru.skillbox.dto.MessageDto;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final RabbitTemplate rabbitTemplate;


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageDto sendMessage(MessageDto messageDTO) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, messageDTO);
        return messageDTO;
    }
}
