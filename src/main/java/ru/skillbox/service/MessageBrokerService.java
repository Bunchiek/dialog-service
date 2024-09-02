package ru.skillbox.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.skillbox.annotation.Loggable;
import ru.skillbox.configuration.RabbitMQConfig;
import ru.skillbox.dto.MessageDto;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageBrokerService {

    private final MessageConsumerService messageConsumerService;

    @Loggable
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(MessageDto messageDTO) {
        messageConsumerService.saveMessage(messageDTO);
    }
}
