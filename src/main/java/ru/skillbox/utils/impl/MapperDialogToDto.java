package ru.skillbox.utils.impl;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.skillbox.dto.DialogDto;
import ru.skillbox.dto.ShortMessageForDialogDto;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.entity.Status;

import java.util.*;


@UtilityClass
@Slf4j
public class MapperDialogToDto {

    public static DialogDto convertDialogToDto(Dialog dialog) {
        if (dialog == null) {
            return null;
        }
        List<Message> messages = Optional.ofNullable(dialog.getMessages()).orElse(Collections.emptyList());
        Long unreadCount = messages.stream()
                .filter(message -> message.getStatus().equals(Status.SENT))
                .count();

        ShortMessageForDialogDto lastMessage = messages.stream()
                .map(MapperMessageToDialogMessageDTO::convertMessageToDto).max(Comparator.comparing(ShortMessageForDialogDto::getTime))
                .orElse(null);


        return DialogDto.builder()
                .id(dialog.getId())
                .lastMessage(lastMessage)
                .unreadCount(unreadCount)
                .conversationPartner1(dialog.getParticipantOne())
                .conversationPartner2(dialog.getParticipantTwo())
                .build();
    }
}
