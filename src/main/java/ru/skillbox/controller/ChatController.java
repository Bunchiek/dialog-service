package ru.skillbox.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.skillbox.annotation.Loggable;
import ru.skillbox.configuration.RabbitMQConfig;
import ru.skillbox.dto.MessageWebSocketDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    @Loggable
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(MessageWebSocketDto messageWebSocketDTO) {


//        String routingKey = "topic.dialog." + messageWebSocketDTO.getData().getId();
//        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, messageWebSocketDTO);
//        log.info("Message sent to RabbitMQ with routing key: " + routingKey);

        String destination = "/topic/dialog/" + messageWebSocketDTO.getData().getId();
        messagingTemplate.convertAndSend(destination, messageWebSocketDTO);
        log.info("Message sent to WebSocket topic: " + destination);
    }
}
