package ru.skillbox.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.skillbox.configuration.RabbitMQConfig;
import ru.skillbox.dto.MessageWebSocketDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageBrokerService {

//    private final SimpMessagingTemplate messagingTemplate;
    private final MessageConsumerService messageConsumerService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(MessageWebSocketDto messageWebSocketDTO) {
        // Обработка сообщения и отправка его обратно клиентам через WebSocket
//        messagingTemplate.convertAndSend("/topic/public", messageDTO);
        messageConsumerService.saveMessage(messageWebSocketDTO);
    }
}
