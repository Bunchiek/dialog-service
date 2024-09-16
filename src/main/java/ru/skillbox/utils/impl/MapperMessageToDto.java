package ru.skillbox.utils.impl;

import lombok.experimental.UtilityClass;
import ru.skillbox.dto.MessageDto;
import ru.skillbox.entity.Message;

@UtilityClass
public class MapperMessageToDto {

    public static MessageDto convertMessageToDto(Message message) {
        if (message == null) {
            return null;
        }

        return MessageDto.builder()
                .id(message.getId())
                .time(message.getTime())
                .authorId(message.getAuthor() != null ? message.getAuthor().getId() : null)
                .recipientId(message.getRecipient() != null ? message.getRecipient().getId() : null)
                .messageText(message.getMessageText())
                .status(message.getStatus())
                .build();
    }
}
