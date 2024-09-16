package ru.skillbox.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import ru.skillbox.dto.GetDialogsRs;
import ru.skillbox.dto.SetStatusMessageReadRs;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.entity.Status;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.repository.DialogRepository;
import ru.skillbox.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.data.domain.Pageable;
import ru.skillbox.dto.*;
import static org.mockito.Mockito.*;

public class DialogServiceImplTest {

    @InjectMocks
    private DialogServiceImpl dialogService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private DialogRepository dialogRepository;

    @Mock
    private MessageRepository messageRepository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new User("550e8400-e29b-41d4-a716-446655440000", "password", new ArrayList<>()),
                null,
                new ArrayList<>()
        );
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testSetStatusMessageRead_Success() {
        UUID companionId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // Используем UUID для идентификатора
        Account companion = new Account();
        companion.setId(companionId);

        Message message1 = new Message();
        message1.setStatus(Status.SENT);
        Dialog dialog = new Dialog();
        message1.setDialog(dialog);

        Message message2 = new Message();
        message2.setStatus(Status.SENT);
        message2.setDialog(dialog);

        dialog.setMessages(List.of(message1, message2));
        dialog.setUnreadCount(1L);
        dialog.setConversationPartner(companion);

        when(accountRepository.findById(companionId)).thenReturn(Optional.of(companion));
        when(dialogRepository.findByConversationPartner(companion)).thenReturn(Optional.of(dialog));
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SetStatusMessageReadRs response = dialogService.setStatusMessageRead(companionId);

        assertNotNull(response);
        assertEquals("OK", response.getData().getMessage());
        assertEquals(0L, dialog.getUnreadCount());

        verify(messageRepository, times(2)).save(any(Message.class)); // Должно быть 2 вызова save
    }

    @Test
    public void testSetStatusMessageRead_CompanionNotFound() {
        UUID companionId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        when(accountRepository.findById(companionId)).thenReturn(Optional.empty());

        SetStatusMessageReadRs response = dialogService.setStatusMessageRead(companionId);

        assertNotNull(response);
        assertEquals("NOK", response.getData().getMessage());
        assertEquals("Пользователь не найден", response.getErrorDescription());
    }

    @Test
    public void testGetAllDialogs() {
        UUID companionId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
        Account account = new Account();
        account.setId(companionId);

        Dialog dialog = new Dialog();
        dialog.setId(1L);
        dialog.setConversationPartner(account);

        Page<Dialog> dialogsPage = new PageImpl<>(List.of(dialog));

        when(dialogRepository.findAll(any(PageRequest.class))).thenReturn(dialogsPage);

        GetDialogsRs response = dialogService.getAllDialogs(PageRequest.of(0, 10));

        assertNotNull(response);
        assertEquals(1, response.getData().size());
        assertEquals(companionId, response.getCurrentUserId());
    }

    @Test
    public void testGetUnreadMessageCount() {
        long count = 5L;
        when(messageRepository.countUnreadMessages(Status.SENT)).thenReturn(count);

        UnreadCountRs response = dialogService.getUnreadMessageCount();

        assertNotNull(response);
        assertEquals(count, response.getData().getCount());
    }

    @Test
    public void testGetAllMessages_Success() {
        UUID companionId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000"); // Используем UUID для идентификатора
        Account companion = new Account();
        companion.setId(companionId);

        UUID authorId = UUID.randomUUID();

        Account author = new Account();
        author.setId(authorId);

        Message message = new Message();
        message.setAuthor(author);
        message.setMessageText("Test message");

        Dialog dialog = new Dialog();
        dialog.setId(1L);
        dialog.setMessages(List.of(message));

        Page<Message> messagesPage = new PageImpl<>(List.of(message));

        when(dialogRepository.findByConversationPartner(any(Account.class))).thenReturn(Optional.of(dialog));
        when(accountRepository.findById(companionId)).thenReturn(Optional.of(companion));
        when(messageRepository.findByDialog(any(Dialog.class), any(Pageable.class))).thenReturn(messagesPage);

        GetMessagesRs response = dialogService.getAllMessages(companionId, PageRequest.of(0, 10));

        assertNotNull(response);
        assertNotNull(response.getData());
        assertEquals(1, response.getData().size());
        assertEquals(authorId, response.getData().get(0).getAuthorId());
    }

    @Test
    public void testGetAllMessages_DialogNotFound() {
        UUID companionId = UUID.randomUUID();
        Pageable pageable = Pageable.ofSize(10);
        Account companion = new Account();

        when(accountRepository.findById(companionId)).thenReturn(Optional.of(companion));
        when(dialogRepository.findByConversationPartner(companion)).thenReturn(Optional.empty());

        GetMessagesRs response = dialogService.getAllMessages(companionId, pageable);

        assertNotNull(response);
        assertEquals("Ошибка", response.getError());
        assertEquals("Диалог не найден", response.getErrorDescription());
    }
}
