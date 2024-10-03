package ru.skillbox.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.annotation.Loggable;
import ru.skillbox.dto.MessageWebSocketDto;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.entity.Status;
import ru.skillbox.repository.DialogRepository;
import ru.skillbox.repository.MessageRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageConsumerService {

    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;

    @Transactional
    @Loggable
    public Message saveMessage(MessageWebSocketDto messageWebSocketDTO) {

        UUID author = messageWebSocketDTO.getData().getConversationPartner1();
        UUID recipient = messageWebSocketDTO.getRecipientId();
        Dialog dialog = findDialogForConversation(author, recipient);

        Message message = new Message();
        message.setTime(messageWebSocketDTO.getData().getTime());
        message.setAuthor(author);
        message.setRecipient(recipient);
        message.setMessageText(messageWebSocketDTO.getData().getMessageText());
        message.setStatus(Status.SENT);
        message.setDialog(dialog);

        return messageRepository.save(message);
    }

    private Dialog findDialogForConversation(UUID author, UUID recipient) {
        return dialogRepository.findByParticipants(author, recipient)
                .orElseThrow(() -> new NoSuchElementException("Диалог не найден"));
    }
}
