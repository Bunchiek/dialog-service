package ru.skillbox.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.skillbox.dto.MessageDto;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.repository.DialogRepository;
import ru.skillbox.repository.MessageRepository;
import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MessageConsumerServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private DialogRepository dialogRepository;

    @InjectMocks
    private MessageConsumerService messageConsumerService;

    private MessageDto messageDto;
    private Account author;
    private Account recipient;
    private Dialog dialog;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        author = new Account();
        author.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
        recipient = new Account();
        recipient.setId(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));

        dialog = new Dialog();
        dialog.setId(1L);
        dialog.setConversationPartner(recipient);
        author.setDialogs(List.of(dialog));

        messageDto = new MessageDto();
        messageDto.setAuthorId(author.getId());
        messageDto.setRecipientId(recipient.getId());
        messageDto.setTime(Instant.now().toEpochMilli());
        messageDto.setMessageText("Test message");
    }

    @Test
    public void testSaveMessage_Success() {
        when(accountRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(accountRepository.findById(recipient.getId())).thenReturn(Optional.of(recipient));
        when(dialogRepository.findById(1L)).thenReturn(Optional.of(dialog));

        messageConsumerService.saveMessage(messageDto);

        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    public void testSaveMessage_AuthorNotFound() {
        when(accountRepository.findById(author.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> messageConsumerService.saveMessage(messageDto));

        verify(messageRepository, never()).save(any(Message.class));
    }

    @Test
    public void testSaveMessage_DialogNotFound() {
        Account authorMock = mock(Account.class);
        Account recipientMock = mock(Account.class);

        when(accountRepository.findById(author.getId())).thenReturn(Optional.of(authorMock));
        when(accountRepository.findById(recipient.getId())).thenReturn(Optional.of(recipientMock));

        when(authorMock.getDialogs()).thenReturn(Collections.emptyList());

        assertThrows(NoSuchElementException.class, () -> messageConsumerService.saveMessage(messageDto));

        verify(messageRepository, never()).save(any(Message.class));
    }

}


