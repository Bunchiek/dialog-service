package ru.skillbox.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.dto.*;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.entity.Status;
import ru.skillbox.repository.DialogRepository;
import ru.skillbox.repository.MessageRepository;
import ru.skillbox.service.DialogService;
import ru.skillbox.utils.GetCurrentUsername;
import ru.skillbox.utils.impl.MapperDialogToDto;
import ru.skillbox.utils.impl.MapperMessageToShortDto;


import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DialogServiceImpl implements DialogService {

//    private final AccountRepository accountRepository;
    private final DialogRepository dialogRepository;
    private final MessageRepository messageRepository;


    @Override
    @Transactional
    public SetStatusMessageReadRs setStatusMessageRead(UUID companionId) {
        SetStatusMessageReadRs response = new SetStatusMessageReadRs();
        try {
            Dialog dialog = dialogRepository
                    .findByParticipants(UUID.fromString(GetCurrentUsername.getCurrentUsername()),companionId)
                    .orElseThrow(() -> new EntityNotFoundException("Диалог не найден"));

            dialog.getMessages().stream()
                    .filter(message -> message.getStatus() == Status.SENT)
                    .forEach(message -> {
                        message.setStatus(Status.READ);
                        messageRepository.save(message);
                    });
            dialog.setUnreadCount(0L);
            dialogRepository.save(dialog);

            response.setData(SetStatusMessageReadDto.builder().message("OK").build());

        } catch (EntityNotFoundException e) {
            response.setData(SetStatusMessageReadDto.builder().message("NOK").build());
            response.setError("Ошибка");
            response.setErrorDescription(e.getMessage());
        }
        response.setTimestamp(Instant.now().toEpochMilli());
        return response;

    }

    @Override
    public GetDialogsRs getAllDialogs(Pageable pageable) {

        List<DialogDto> dialogDtoList = new ArrayList<>();
        UUID currentUser = UUID.fromString(GetCurrentUsername.getCurrentUsername());
        try {
            List<Dialog> dialogs = getDialogsByUser(currentUser);
            dialogDtoList = dialogs.stream()
                    .map(MapperDialogToDto::convertDialogToDto)
                    .toList();
        } catch (EntityNotFoundException e) {
            log.info(e.getMessage());
        }

        return GetDialogsRs.builder()
                .timestamp(Instant.now().toEpochMilli())
                .total(dialogDtoList.size())
                .offset((int) pageable.getOffset())
                .perPage(pageable.getPageSize())
                .content(dialogDtoList)
                .currentUserId(currentUser)
                .build();
    }


    @Override
    public UnreadCountRs getUnreadMessageCount() {
        long unreadCount = messageRepository.countUnreadMessages(Status.SENT);
        return UnreadCountRs.builder()
                .timestamp(Instant.now().toEpochMilli())
                .data(UnreadCountDto.builder().count(unreadCount).build())
                .build();
    }

    @Override
    public GetMessagesRs getAllMessages(UUID companionId, Pageable pageable) {
        GetMessagesRs response = new GetMessagesRs();
//        response.setData(new ArrayList<>());
        try {
//            Account companion = accountRepository.findById(companionId)
//                    .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

            Dialog dialog = dialogRepository
                    .findByParticipants(UUID.fromString(GetCurrentUsername.getCurrentUsername()),companionId)
                    .orElseGet(() -> createDialog(companionId));

            Page<Message> messagesPage = messageRepository.findByDialog(dialog, pageable);
            List<MessageShortDto> messageDtos = messagesPage.getContent().stream()
                    .map(MapperMessageToShortDto::convertMessageToShortDto)
                    .toList();

            response.setTotal((int) messagesPage.getTotalElements());
            response.setOffset((int) pageable.getOffset());
            response.setPerPage(pageable.getPageSize());
            response.setData(messageDtos);
            response.setTimestamp(Instant.now().toEpochMilli());

        } catch (EntityNotFoundException e) {
            response.setError("Ошибка");
            response.setErrorDescription(e.getMessage());
            response.setTimestamp(Instant.now().toEpochMilli());
        }
        return response;
    }

//    @Transactional
//    public Optional<Account> findById(UUID id) {
//        return accountRepository.findByIdWithDialogsAndMessages(id);
//    }

    @Transactional
    private Dialog createDialog(UUID companion) {
//        Account participantOne = accountRepository.findById(UUID.fromString(GetCurrentUsername.getCurrentUsername()))
//                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        Dialog dialog = new Dialog();
        dialog.setParticipantOne(UUID.fromString(GetCurrentUsername.getCurrentUsername()));
        dialog.setParticipantTwo(companion);
        dialog.setUnreadCount(0L);
        return dialogRepository.save(dialog); // Сохранение нового диалога
    }

    public List<Dialog> getDialogsByUser(UUID userId) {
        return dialogRepository.findByParticipantOneOrParticipantTwo(userId, userId);
    }

}
