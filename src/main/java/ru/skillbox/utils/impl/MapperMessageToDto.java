package ru.skillbox.utils.impl;

import lombok.experimental.UtilityClass;
import ru.skillbox.dto.MessageDto;
import ru.skillbox.dto.MessageRs;
import ru.skillbox.entity.Message;

@UtilityClass
public class MapperMessageToDto {

    public static MessageDto convertMessageToDto(Message message) {
        if (message == null) {
            return null;
        }
        MessageRs messageRs = MessageRs.builder()
                .messageText(message.getMessageText())
                .time(message.getTime())
                .readStatus(message.getStatus())
                .conversationPartner1(message.getAuthor() != null ? message.getAuthor().getId() : null)
                .conversationPartner2(message.getRecipient() != null ? message.getRecipient().getId() : null)
                .build();

        return MessageDto.builder()
                .data(messageRs)
                .recipientId(message.getRecipient() != null ? message.getRecipient().getId() : null)
                .build();
    }
}
