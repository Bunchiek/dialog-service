package ru.skillbox.utils.impl;

import lombok.experimental.UtilityClass;
import ru.skillbox.dto.MessageShortDto;
import ru.skillbox.entity.Message;

import java.util.UUID;

@UtilityClass
public class MapperMessageToShortDto {


    public static MessageShortDto convertMessageToShortDto(Message message) {
        if (message == null) {
            return null;
        }
        UUID author = message.getAuthor();
        if (author == null) {
            return MessageShortDto.builder()
                    .authorId(null)
                    .messageText(message.getMessageText())
                    .build();
        }
        return MessageShortDto.builder()
                .authorId(author)
                .messageText(message.getMessageText())
                .build();
    }
}
