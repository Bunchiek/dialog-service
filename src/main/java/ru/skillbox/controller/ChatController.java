package ru.skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.skillbox.annotation.Loggable;
import ru.skillbox.configuration.RabbitMQConfig;
import ru.skillbox.dto.MessageWebSocketDto;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final RabbitTemplate rabbitTemplate;

    @Loggable
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(MessageWebSocketDto messageWebSocketDTO) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "chat.public", messageWebSocketDTO);
        log.info("Message successfully sent to RabbitMQ");
    }
}
