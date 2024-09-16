package ru.skillbox.utils.impl;

import lombok.experimental.UtilityClass;
import ru.skillbox.dto.MessageShortDto;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Message;

@UtilityClass
public class MapperMessageToShortDto {


    public static MessageShortDto convertMessageToShortDto(Message message) {
        if (message == null) {
            return null;
        }
        Account author = message.getAuthor();
        if (author == null) {
            return MessageShortDto.builder()
                    .authorId(null)
                    .messageText(message.getMessageText())
                    .build();
        }
        return MessageShortDto.builder()
                .authorId(author.getId())
                .messageText(message.getMessageText())
                .build();
    }
}
