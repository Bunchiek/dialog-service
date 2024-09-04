package ru.skillbox.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.dto.*;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.entity.Status;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.repository.DialogRepository;
import ru.skillbox.repository.MessageRepository;
import ru.skillbox.service.DialogService;


import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DialogServiceImpl implements DialogService {

    private final AccountRepository accountRepository;
    private final DialogRepository dialogRepository;
    private final MessageRepository messageRepository;


    @Override
    public SetStatusMessageReadRs setStatusMessageRead(Long companionId) {
        SetStatusMessageReadRs response = new SetStatusMessageReadRs();
        try {
            Account companion = accountRepository.findById(companionId)
                    .orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));

            Dialog dialog = dialogRepository.findByConversationPartner(companion)
                    .orElseThrow(() -> new NoSuchElementException("Диалог не найден"));

            dialog.getMessages().stream()
                    .filter(message -> message.getStatus() == Status.SENT)
                    .forEach(message -> {
                        message.setStatus(Status.READ);
                        messageRepository.save(message);
                    });
            dialog.setUnreadCount(0L);
            dialogRepository.save(dialog);

            response.setData(SetStatusMessageReadDto.builder().message("OK").build());

        } catch (NoSuchElementException e) {
            response.setData(SetStatusMessageReadDto.builder().message("NOK").build());
            response.setError("Ошибка");
            response.setErrorDescription(e.getMessage());
        }
        response.setTimestamp(Instant.now().toEpochMilli());
        return response;

    }

    @Override
    public GetDialogsRs getAllDialogs(Pageable pageable) {

        Page<Dialog> dialogs = dialogRepository.findAll(pageable);
        List<DialogDto> dialogDtoList = dialogs.getContent().stream()
                .map(this::convertToDto)
                .toList();

        return GetDialogsRs.builder()
                .timestamp(Instant.now().toEpochMilli())
                .total((int) dialogs.getTotalElements())
                .offset((int) pageable.getOffset())
                .perPage(pageable.getPageSize())
                .data(dialogDtoList)
                .currentUserId(1L)
                .build();
    }


    @Override
    public UnreadCountRs getUnreadMessageCount() {
        return UnreadCountRs.builder()
                .timestamp(Instant.now().toEpochMilli())
                .data(UnreadCountDto.builder().count(messageRepository.countUnreadMessages(Status.SENT)).build())
                .build();
    }

    @Override
    public GetMessagesRs getAllMessages(Long companionId, Pageable pageable) {
        
        GetMessagesRs response = new GetMessagesRs();

        try {
            Account companion = accountRepository.findById(companionId)
                    .orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));

            Dialog dialog = dialogRepository.findByConversationPartner(companion)
                    .orElseThrow(() -> new NoSuchElementException("Диалог не найден"));

            Page<Message> messagesPage = messageRepository.findByDialog(dialog, pageable);

            List<MessageShortDto> messageDtos = messagesPage.stream()
                    .map(this::convertMessageToShortDto)
                    .collect(Collectors.toList());

            response.setTotal((int) messagesPage.getTotalElements());
            response.setOffset((int) pageable.getOffset());
            response.setPerPage(pageable.getPageSize());
            response.setData(messageDtos);
            response.setTimestamp(Instant.now().toEpochMilli());

        } catch (NoSuchElementException e) {
            response.setError("Ошибка");
            response.setErrorDescription(e.getMessage());
            response.setTimestamp(Instant.now().toEpochMilli());
        }

        return response;

    }

    private MessageShortDto  convertMessageToShortDto(Message message) {
        return MessageShortDto.builder()
                .id(message.getId())
                .authorId(message.getAuthor().getId())
                .time(message.getTime())
                .messageText(message.getMessageText())
                .build();
    }


    public DialogDto convertToDto(Dialog dialog) {
        if (dialog == null) {
            return null;
        }
        Message lastMessage = null;
        try {
            lastMessage = dialog.getMessages().getLast();
        } catch (NoSuchElementException e) {
            log.info("В диалоге {} нет сообщений ", dialog.getId() + e.getMessage());
        }
        return DialogDto.builder()
                .id(dialog.getId())
                .unreadCount(dialog.getUnreadCount())
                .conversationPartner(convertAccountToDto(dialog.getConversationPartner()))
                .lastMessage(lastMessage != null ? convertMessageToDto(lastMessage) : null)
                .build();
    }

    private AccountDto convertAccountToDto(Account account) {
        if (account == null) {
            return null;
        }
        return AccountDto.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .build();
    }

    private MessageDto convertMessageToDto(Message message) {
        if (message == null) {
            return null;
        }
        return MessageDto.builder()
                .id(message.getId())
                .time(message.getTime())
                .authorId(message.getAuthor().getId())
                .recipientId(message.getRecipient().getId())
                .messageText(message.getMessageText())
                .status(message.getStatus())
                .build();
    }

}
