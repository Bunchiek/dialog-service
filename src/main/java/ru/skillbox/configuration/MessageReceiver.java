package ru.skillbox.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.skillbox.dto.MessageDto;
import ru.skillbox.service.MessageService;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class MessageReceiver {

    private final MessageService messageService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(MessageDto messageDTO) {
        messageService.saveMessage(messageDTO);
    }
}
