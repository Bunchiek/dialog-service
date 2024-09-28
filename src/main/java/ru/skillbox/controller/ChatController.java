package ru.skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import ru.skillbox.annotation.Loggable;
import ru.skillbox.configuration.RabbitMQConfig;
import ru.skillbox.dto.MessageDto;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final RabbitTemplate rabbitTemplate;

    @Loggable
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(MessageDto messageDTO) {
        log.info("Received message: {}", messageDTO);
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "chat.public", messageDTO);
            log.info("Message successfully sent to RabbitMQ");
        } catch (Exception e) {
            log.error("Error sending message to RabbitMQ", e);
        }
    }
}
