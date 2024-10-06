package ru.skillbox.utils.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.skillbox.dto.DialogDto;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;
import ru.skillbox.entity.Status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

class MapperDialogToDtoTest {

    @Test
    void convertDialogToDto_shouldReturnCorrectDto() {
        Dialog dialog = Mockito.mock(Dialog.class);

        Message message1 = Mockito.mock(Message.class);
        Message message2 = Mockito.mock(Message.class);

        LocalDateTime time1 = LocalDateTime.of(2024, 10, 5, 10, 0);
        LocalDateTime time2 = LocalDateTime.of(2024, 10, 6, 12, 0);
        UUID authorId = UUID.randomUUID();
        UUID recipientId = UUID.randomUUID();

        doReturn(time1).when(message1).getTime();
        doReturn(Status.SENT).when(message1).getStatus();
        doReturn(time2).when(message2).getTime();
        doReturn(Status.READ).when(message2).getStatus();

        List<Message> messages = Arrays.asList(message1, message2);

        doReturn(1L).when(dialog).getId();
        doReturn(authorId).when(dialog).getParticipantOne();
        doReturn(recipientId).when(dialog).getParticipantTwo();
        doReturn(messages).when(dialog).getMessages();

        DialogDto dto = MapperDialogToDto.convertDialogToDto(dialog);

        assertNotNull(dto);

        assertEquals(1L, dto.getId());
        assertEquals(authorId, dto.getConversationPartner1());
        assertEquals(recipientId, dto.getConversationPartner2());
        assertEquals(1, dto.getUnreadCount());
    }

    @Test
    void convertDialogToDto_shouldReturnDtoWithNoMessages() {
        Dialog dialog = Mockito.mock(Dialog.class);

        UUID participantOne = UUID.randomUUID();
        UUID participantTwo = UUID.randomUUID();

        doReturn(1L).when(dialog).getId();
        doReturn(participantOne).when(dialog).getParticipantOne();
        doReturn(participantTwo).when(dialog).getParticipantTwo();
        doReturn(Collections.emptyList()).when(dialog).getMessages();

        DialogDto dto = MapperDialogToDto.convertDialogToDto(dialog);

        assertNotNull(dto);

        assertEquals(1L, dto.getId());
        assertEquals(participantOne, dto.getConversationPartner1());
        assertEquals(participantTwo, dto.getConversationPartner2());
        assertNull(dto.getLastMessage());
        assertEquals(0, dto.getUnreadCount()); // Нет непрочитанных сообщений
    }

    @Test
    void convertDialogToDto_shouldReturnNullWhenDialogIsNull() {
        DialogDto dto = MapperDialogToDto.convertDialogToDto(null);

        assertNull(dto);
    }
}
