package ru.skillbox.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.skillbox.configuration.RabbitMQConfig;
import ru.skillbox.dto.MessageWebSocketDto;
import ru.skillbox.dto.MessageWebSocketRs;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageBrokerService {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageConsumerService messageConsumerService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(MessageWebSocketDto messageWebSocketDTO) {
        // Отправляем сообщение обратно клиентам через WebSocket
        MessageWebSocketRs rs = messageWebSocketDTO.getData();
        rs.setId(1L);
        messageWebSocketDTO.setData(rs);
        messagingTemplate.convertAndSend("/topic/public", messageWebSocketDTO);
        messageConsumerService.saveMessage(messageWebSocketDTO);
    }
}
