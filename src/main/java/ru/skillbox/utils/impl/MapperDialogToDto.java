package ru.skillbox.utils.impl;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.skillbox.dto.DialogDto;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static ru.skillbox.utils.impl.MapperAccountToDto.convertAccountToDto;
import static ru.skillbox.utils.impl.MapperMessageToDto.convertMessageToDto;

@UtilityClass
@Slf4j
public class MapperDialogToDto {

    public static DialogDto convertDialogToDto(Dialog dialog) {
        if (dialog == null) {
            return null;
        }
        List<Message> messages = dialog.getMessages() != null ? dialog.getMessages() : Collections.emptyList();

        Message lastMessage = messages.isEmpty() ? null : messages.get(messages.size() - 1);

        return DialogDto.builder()
                .id(dialog.getId())
                .lastMessage(lastMessage != null ? MapperMessageToDto.convertMessageToDto(lastMessage) : null)
                .unreadCount(dialog.getUnreadCount())
                .build();
    }
}
