package ru.skillbox.utils.impl;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.skillbox.dto.DialogDto;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;

import java.util.NoSuchElementException;

import static ru.skillbox.utils.impl.MapperAccountToDto.convertAccountToDto;
import static ru.skillbox.utils.impl.MapperMessageToDto.convertMessageToDto;

@UtilityClass
@Slf4j
public class MapperDialogToDto {

    public static DialogDto convertToDto(Dialog dialog) {
        if (dialog == null) {
            return null;
        }
        Message lastMessage = null;
        try {
            lastMessage = dialog.getMessages().getLast();
        } catch (NoSuchElementException e) {
            log.info("В диалоге {} нет сообщений ", dialog.getId() + e.getMessage());
        }
        return DialogDto.builder()
                .id(dialog.getId())
                .unreadCount(dialog.getUnreadCount())
                .conversationPartner(convertAccountToDto(dialog.getConversationPartner()))
                .lastMessage(lastMessage != null ? convertMessageToDto(lastMessage) : null)
                .build();
    }

}
