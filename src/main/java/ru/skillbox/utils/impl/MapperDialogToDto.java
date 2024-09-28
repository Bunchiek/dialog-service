package ru.skillbox.utils.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.skillbox.dto.DialogDto;
//import ru.skillbox.entity.Account;
import ru.skillbox.dto.MessageTestDto;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
//import ru.skillbox.repository.AccountRepository;

import java.util.*;


@UtilityClass
@Slf4j
public class MapperDialogToDto {

    public static DialogDto convertDialogToDto(Dialog dialog) {
        if (dialog == null) {
            return null;
        }
        Set<Message> messages = Optional.ofNullable(dialog.getMessages()).orElse(Collections.emptySet());
//        Message lastMessage = messages.stream()
//                .max(Comparator.comparing(Message::getTime))
//                .orElse(null);

        List<MessageTestDto> lastMessage = messages.stream()
                .map(MapperMessageToTempDTO::convertMessageToDto)
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
