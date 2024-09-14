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
import ru.skillbox.utils.impl.MapperDialogToDto;
import ru.skillbox.utils.impl.MapperMessageToShortDto;


import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
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
                .map(MapperDialogToDto::convertToDto)
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
    public GetMessagesRs getAllMessages(UUID companionId, Pageable pageable) {
        
        GetMessagesRs response = new GetMessagesRs();

        try {
            Account companion = accountRepository.findById(companionId)
                    .orElseThrow(() -> new NoSuchElementException("Пользователь не найден"));

            Dialog dialog = dialogRepository.findByConversationPartner(companion)
                    .orElseThrow(() -> new NoSuchElementException("Диалог не найден"));

            Page<Message> messagesPage = messageRepository.findByDialog(dialog, pageable);

            List<MessageShortDto> messageDtos = messagesPage.stream()
                    .map(MapperMessageToShortDto::convertMessageToShortDto)
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

}
