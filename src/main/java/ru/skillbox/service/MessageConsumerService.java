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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;
    private final DialogRepository dialogRepository;

    @Transactional
    @Loggable
    public void saveMessage(MessageDto messageDTO) {

        Account author = accountRepository.findById(messageDTO.getData().getConversationPartner1())
                .orElseThrow(() -> new NoSuchElementException("Отправитель не найден"));

        Account recipient = accountRepository.findById(messageDTO.getRecipientId())
                .orElseThrow(() -> new NoSuchElementException("Получатель не найден"));

        Dialog dialog = findDialogForConversation(author, recipient);

        Message message = new Message();
        message.setTime(messageDTO.getData().getTime());
        message.setAuthor(author);
        message.setRecipient(recipient);
        message.setMessageText(messageDTO.getData().getMessageText());
        message.setStatus(Status.SENT);
        message.setDialog(dialog);

        messageRepository.save(message);
    }

    private Dialog findDialogForConversation(Account author, Account recipient) {
        return dialogRepository.findByParticipants(author.getId(), recipient.getId())
                .orElseThrow(() -> new NoSuchElementException("Диалог не найден"));
    }
}
