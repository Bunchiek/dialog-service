package ru.skillbox.utils.impl;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.skillbox.dto.DialogDto;
import ru.skillbox.dto.MessageTestDto;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;

import java.util.*;


@UtilityClass
@Slf4j
public class MapperDialogToDto {

    public static DialogDto convertDialogToDto(Dialog dialog) {
        if (dialog == null) {
            return null;
        }
        List<Message> messages = Optional.ofNullable(dialog.getMessages()).orElse(Collections.emptyList());

        List<MessageTestDto> lastMessage = messages.stream()
                .map(MapperMessageToTempDTO::convertMessageToDto)
                .sorted(Comparator.comparing(MessageTestDto::getTime))
                .toList();

        return DialogDto.builder()
                .id(dialog.getId())
                .lastMessage(lastMessage)
                .unreadCount(dialog.getUnreadCount())
                .conversationPartner1(dialog.getParticipantOne())
                .conversationPartner2(dialog.getParticipantTwo())
                .build();
    }
}
