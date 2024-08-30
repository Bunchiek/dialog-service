package ru.skillbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.dto.MessageDto;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.entity.Status;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.repository.DialogRepository;
import ru.skillbox.repository.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
    private final DialogRepository dialogRepository;

    @Transactional
    public void saveMessage(MessageDto messageDTO) {
        Account author = accountRepository.findById(messageDTO.getAuthorId()).orElseThrow();
        Account recipient = accountRepository.findById(messageDTO.getRecipientId()).orElseThrow();
        Dialog dialog = dialogRepository.findById(1L).orElseThrow();

        Message message = Message.builder()
                .time(messageDTO.getTime())
                .author(author)
                .recipient(recipient)
                .messageText(messageDTO.getMessageText())
                .status(Status.SENT)
                .dialog(dialog)
                .build();
        messageRepository.save(message);

    }
}
