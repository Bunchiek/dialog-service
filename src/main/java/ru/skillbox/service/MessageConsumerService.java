package ru.skillbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.annotation.Loggable;
import ru.skillbox.dto.MessageDto;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.entity.Status;
import ru.skillbox.repository.AccountRepository;
import ru.skillbox.repository.DialogRepository;
import ru.skillbox.repository.MessageRepository;
import ru.skillbox.utils.MapperFactory;

@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
    private final DialogRepository dialogRepository;

    @Transactional
    @Loggable
    public void saveMessage(MessageDto messageDTO) {
        Account author = accountRepository.findById(messageDTO.getAuthorId()).orElseThrow();
        Account recipient = accountRepository.findById(messageDTO.getRecipientId()).orElseThrow();
        Dialog dialog = dialogRepository.findById(1L).orElseThrow();

        Message message = new Message();
        message.setTime(messageDTO.getTime());
        message.setAuthor(author);
        message.setRecipient(recipient);
        message.setMessageText(message.getMessageText());
        message.setStatus(Status.SENT);
        message.setDialog(dialog);
        messageRepository.save(message);

    }
}
