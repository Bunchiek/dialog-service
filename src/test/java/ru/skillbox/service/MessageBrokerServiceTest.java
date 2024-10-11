package ru.skillbox.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.skillbox.dto.MessageWebSocketDto;
import ru.skillbox.dto.MessageWebSocketRs;
import ru.skillbox.dto.kafka.MessageNotification;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.repository.DialogRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageBrokerServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @Mock
    private MessageConsumerService messageConsumerService;

    @Mock
    private DialogRepository dialogRepository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private MessageBrokerService messageBrokerService;

    private MessageWebSocketDto messageWebSocketDto;
    private Dialog dialog;
    private Message message;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Инициализация тестовых данных
        messageWebSocketDto = new MessageWebSocketDto();
        MessageWebSocketRs data = new MessageWebSocketRs();
        data.setConversationPartner1(UUID.randomUUID());
        data.setConversationPartner2(UUID.randomUUID());
        data.setMessageText("Hello World");
        data.setTime(LocalDateTime.now());
        messageWebSocketDto.setData(data);

        dialog = new Dialog();
        dialog.setId(1L);

        message = new Message();
        message.setId(100L);
    }

    @Test
    void receiveMessage_shouldSendWebSocketAndKafkaMessage_whenDialogExists() {
        // Мокаем поведение
        when(dialogRepository.findByParticipants(any(UUID.class), any(UUID.class)))
                .thenReturn(Optional.of(dialog));
        when(messageConsumerService.saveMessage(any(MessageWebSocketDto.class), any(Dialog.class)))
                .thenReturn(message);

        // Выполняем тестируемый метод
        messageBrokerService.receiveMessage(messageWebSocketDto);

        // Проверяем, что сообщение отправляется через WebSocket
        verify(messagingTemplate, times(1))
                .convertAndSend(eq("/topic/" + messageWebSocketDto.getData().getConversationPartner2()+messageWebSocketDto.getData().getConversationPartner1()), eq(messageWebSocketDto));

        // Проверяем отправку уведомления в Kafka
        ArgumentCaptor<MessageNotification> kafkaCaptor = ArgumentCaptor.forClass(MessageNotification.class);
        verify(kafkaTemplate, times(1)).send(anyString(), kafkaCaptor.capture());

        // Проверка содержимого отправленного сообщения в Kafka
        MessageNotification sentNotification = kafkaCaptor.getValue();
        assertEquals("Hello World", sentNotification.getContent());
        assertEquals(messageWebSocketDto.getData().getConversationPartner1().toString(), sentNotification.getAuthorId());
        assertEquals(messageWebSocketDto.getData().getConversationPartner2().toString(), sentNotification.getToUserId());
        assertEquals("MESSAGE", sentNotification.getNotificationType());
    }

    @Test
    void receiveMessage_shouldHandleEntityNotFoundException() {
        // Given
        MessageWebSocketDto messageWebSocketDto = new MessageWebSocketDto();
        MessageWebSocketRs messageData = new MessageWebSocketRs();
        messageData.setConversationPartner1(UUID.randomUUID());
        messageData.setConversationPartner2(UUID.randomUUID());
        messageData.setMessageText("Hello World");
        messageWebSocketDto.setData(messageData);

        // Мокаем поведение, чтобы репозиторий выбросил исключение EntityNotFoundException
        when(dialogRepository.findByParticipants(any(UUID.class), any(UUID.class)))
                .thenThrow(new EntityNotFoundException("Диалог не найден"));

        // When
        messageBrokerService.receiveMessage(messageWebSocketDto);

        // Then
        // Проверяем, что метод convertAndSend не был вызван
        verify(messagingTemplate, never()).convertAndSend(anyString(), any(MessageWebSocketDto.class));

    }
}
