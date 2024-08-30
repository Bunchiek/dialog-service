package ru.skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.skillbox.configuration.RabbitMQConfig;
import ru.skillbox.dto.MessageDto;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final RabbitTemplate rabbitTemplate;


    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageDto sendMessage(MessageDto messageDTO) {
        log.info("Объект из Websocket {}",messageDTO);
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, messageDTO);
        return messageDTO;
    }
}
