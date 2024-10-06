package ru.skillbox.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.skillbox.dto.MessageWebSocketDto;
import ru.skillbox.dto.MessageWebSocketRs;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.entity.Status;
import ru.skillbox.repository.DialogRepository;
import ru.skillbox.repository.MessageRepository;
import ru.skillbox.service.MessageConsumerService;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageConsumerServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private DialogRepository dialogRepository;

    @InjectMocks
    private MessageConsumerService messageConsumerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveMessage_shouldSaveMessageSuccessfully() {
        Dialog dialog = mock(Dialog.class);

        UUID authorId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();
        LocalDateTime time = LocalDateTime.now();
        String messageText = "Test message";

        MessageWebSocketRs messageWebSocketRs = MessageWebSocketRs.builder()
                .conversationPartner1(authorId)
                .conversationPartner2(recipientId)
                .time(time)
                .messageText(messageText)
                .readStatus(Status.SENT)
                .build();

        MessageWebSocketDto messageWebSocketDto = MessageWebSocketDto.builder()
                .recipientId(recipientId)
                .data(messageWebSocketRs)
                .build();

        when(dialogRepository.findByParticipants(any(UUID.class), any(UUID.class))).thenReturn(Optional.of(dialog));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Message savedMessage = messageConsumerService.saveMessage(messageWebSocketDto);

        assertNotNull(savedMessage);
        assertEquals(authorId, savedMessage.getAuthor());
        assertEquals(recipientId, savedMessage.getRecipient());
        assertEquals(time, savedMessage.getTime());
        assertEquals(messageText, savedMessage.getMessageText());
        assertEquals(Status.SENT, savedMessage.getStatus());
        assertEquals(dialog, savedMessage.getDialog());

        verify(dialogRepository).findByParticipants(authorId, recipientId);
        verify(messageRepository).save(any(Message.class));
    }

    @Test
    void saveMessage_shouldThrowExceptionWhenDialogNotFound() {
        UUID authorId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();

        MessageWebSocketRs messageWebSocketRs = MessageWebSocketRs.builder()
                .conversationPartner1(authorId)
                .conversationPartner2(recipientId)
                .build();

        MessageWebSocketDto messageWebSocketDto = MessageWebSocketDto.builder()
                .recipientId(recipientId)
                .data(messageWebSocketRs)
                .build();

        when(dialogRepository.findByParticipants(any(UUID.class), any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            messageConsumerService.saveMessage(messageWebSocketDto);
        });

        verify(messageRepository, never()).save(any(Message.class));
    }

    @Test
    void findDialogForConversation_shouldReturnDialogWhenFound() {
        UUID authorId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();
        Dialog dialog = mock(Dialog.class);

        when(dialogRepository.findByParticipants(authorId, recipientId)).thenReturn(Optional.of(dialog));

        Dialog foundDialog = messageConsumerService.findDialogForConversation(authorId, recipientId);

        assertNotNull(foundDialog);
        assertEquals(dialog, foundDialog);

        verify(dialogRepository).findByParticipants(authorId, recipientId);
    }

    @Test
    void findDialogForConversation_shouldThrowExceptionWhenDialogNotFound() {
        UUID authorId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();

        when(dialogRepository.findByParticipants(authorId, recipientId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            messageConsumerService.findDialogForConversation(authorId, recipientId);
        });

        verify(dialogRepository).findByParticipants(authorId, recipientId);
    }
}
