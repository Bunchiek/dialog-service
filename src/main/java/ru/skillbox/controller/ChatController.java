//package ru.skillbox.controller;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import ru.skillbox.annotation.Loggable;
//import ru.skillbox.configuration.RabbitMQConfig;
//import ru.skillbox.dto.MessageWebSocketDto;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import ru.skillbox.dto.MessageWebSocketRs;
//import ru.skillbox.entity.Message;
//import ru.skillbox.service.MessageConsumerService;
//
//@Controller
//@RequiredArgsConstructor
//@Slf4j
//public class ChatController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//    private final MessageConsumerService messageConsumerService;
//
//    @Loggable
//    @MessageMapping("/chat.sendMessage")
//    public void sendMessage(MessageWebSocketDto messageWebSocketDTO) {
//
//
////        String routingKey = "topic.dialog." + messageWebSocketDTO.getData().getId();
////        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, messageWebSocketDTO);
////        log.info("Message sent to RabbitMQ with routing key: " + routingKey);
//
//        Message newMessage = messageConsumerService.saveMessage(messageWebSocketDTO);
//
//        MessageWebSocketRs rs = messageWebSocketDTO.getData();
//        rs.setId(newMessage.getId());
//        messageWebSocketDTO.setData(rs);
//        log.info("Message sent to {}", messageWebSocketDTO);
//
//
//        String destination = "/topic/dialog/" + messageWebSocketDTO.getData().getId();
//        messagingTemplate.convertAndSend(destination, messageWebSocketDTO);
//        log.info("Message sent to WebSocket topic: " + destination);
//    }
//}
