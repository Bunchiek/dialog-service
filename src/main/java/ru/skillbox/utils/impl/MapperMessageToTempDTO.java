package ru.skillbox.utils.impl;

import ru.skillbox.dto.MessageDto;
import ru.skillbox.dto.MessageRs;
import ru.skillbox.dto.MessageTestDto;
import ru.skillbox.entity.Message;

public class MapperMessageToTempDTO {

    public static MessageTestDto convertMessageToDto(Message message) {
        if (message == null) {
            return null;
        }

        return MessageTestDto.builder()
                .time(message.getTime())
                .authorId(message.getAuthor())
                .messageText(message.getMessageText())
                .readStatus(message.getStatus())
                .build();
    }

}
