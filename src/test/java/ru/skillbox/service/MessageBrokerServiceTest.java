package ru.skillbox.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.skillbox.configuration.RabbitMQConfig;
import ru.skillbox.dto.MessageWebSocketDto;
import ru.skillbox.dto.MessageWebSocketRs;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.repository.DialogRepository;
import ru.skillbox.service.MessageBrokerService;
import ru.skillbox.service.MessageConsumerService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MessageBrokerServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private MessageConsumerService messageConsumerService;

    @Mock
    private DialogRepository dialogRepository;

    @InjectMocks
    private MessageBrokerService messageBrokerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void receiveMessage_shouldProcessAndSendWebSocketMessage() {
        UUID conversationPartner1 = UUID.randomUUID();
        UUID conversationPartner2 = UUID.randomUUID();
        Long dialogId = 1L;
        Long messageId = 1L;

        Dialog dialog = mock(Dialog.class);
        when(dialog.getId()).thenReturn(dialogId);

        MessageWebSocketRs messageWebSocketRs = MessageWebSocketRs.builder()
                .conversationPartner1(conversationPartner1)
                .conversationPartner2(conversationPartner2)
                .build();

        MessageWebSocketDto messageWebSocketDto = MessageWebSocketDto.builder()
                .data(messageWebSocketRs)
                .build();

        Message savedMessage = mock(Message.class);
        when(savedMessage.getId()).thenReturn(messageId);

        when(dialogRepository.findByParticipants(conversationPartner1, conversationPartner2)).thenReturn(Optional.of(dialog));
        when(messageConsumerService.saveMessage(any(MessageWebSocketDto.class))).thenReturn(savedMessage);

        messageBrokerService.receiveMessage(messageWebSocketDto);

        assertEquals(dialogId, messageWebSocketDto.getData().getId());

        verify(messageConsumerService).saveMessage(messageWebSocketDto);

        String expectedTopic = "/topic/" + dialogId;
        verify(messagingTemplate).convertAndSend(expectedTopic, messageWebSocketDto);
    }

    @Test
    public void receiveMessage_shouldHandleDialogNotFoundException() {
        UUID conversationPartner1 = UUID.randomUUID();
        UUID conversationPartner2 = UUID.randomUUID();

        MessageWebSocketRs messageWebSocketRs = MessageWebSocketRs.builder()
                .conversationPartner1(conversationPartner1)
                .conversationPartner2(conversationPartner2)
                .build();

        MessageWebSocketDto messageWebSocketDto = MessageWebSocketDto.builder()
                .data(messageWebSocketRs)
                .build();

        when(dialogRepository.findByParticipants(conversationPartner1, conversationPartner2)).thenReturn(Optional.empty());

        messageBrokerService.receiveMessage(messageWebSocketDto);

        verify(messageConsumerService, never()).saveMessage(any(MessageWebSocketDto.class));
        verify(messagingTemplate, never()).convertAndSend(anyString(), any(MessageWebSocketDto.class));
    }

    @Test
    public void receiveMessage_shouldHandleExceptionDuringProcessing() {
        UUID conversationPartner1 = UUID.randomUUID();
        UUID conversationPartner2 = UUID.randomUUID();

        MessageWebSocketRs messageWebSocketRs = MessageWebSocketRs.builder()
                .conversationPartner1(conversationPartner1)
                .conversationPartner2(conversationPartner2)
                .build();

        MessageWebSocketDto messageWebSocketDto = MessageWebSocketDto.builder()
                .data(messageWebSocketRs)
                .build();

        Dialog dialog = mock(Dialog.class);
        when(dialogRepository.findByParticipants(conversationPartner1, conversationPartner2)).thenReturn(Optional.of(dialog));

        when(messageConsumerService.saveMessage(any(MessageWebSocketDto.class))).thenThrow(new RuntimeException("Test exception"));

        messageBrokerService.receiveMessage(messageWebSocketDto);

        verify(messagingTemplate, never()).convertAndSend(anyString(), any(MessageWebSocketDto.class));
    }
}
