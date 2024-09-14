package ru.skillbox.utils.impl;

import lombok.experimental.UtilityClass;
import ru.skillbox.dto.MessageShortDto;
import ru.skillbox.entity.Message;

@UtilityClass
public class MapperMessageToShortDto {


    public static MessageShortDto convertMessageToShortDto(Message message) {
        return MessageShortDto.builder()
                .id(message.getId())
                .authorId(message.getAuthor().getId())
                .time(message.getTime())
                .messageText(message.getMessageText())
                .build();
    }
}
