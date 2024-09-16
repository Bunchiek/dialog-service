package ru.skillbox.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skillbox.dto.MessageDto;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageBrokerServiceTest {

    @InjectMocks
    private MessageBrokerService messageBrokerService;

    @Mock
    private MessageConsumerService messageConsumerService;

    @Test
    public void testReceiveMessage() {
        // Создаем тестовый объект MessageDto
        MessageDto messageDto = new MessageDto();
        messageDto.setAuthorId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        messageDto.setRecipientId(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
        messageDto.setMessageText("Test message");

        messageBrokerService.receiveMessage(messageDto);

        verify(messageConsumerService, times(1)).saveMessage(messageDto);
    }
}
