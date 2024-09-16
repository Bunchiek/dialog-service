package ru.skillbox.utils.impl;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.skillbox.dto.DialogDto;
import ru.skillbox.entity.Dialog;
import ru.skillbox.entity.Message;

import java.util.Collections;

public class MapperDialogToDtoTest {



    @Test
    public void testConvertDialogToDto_WithValidDialog() {
        Dialog dialog = new Dialog();
        dialog.setId(1L);
        dialog.setUnreadCount(10L);

        Message lastMessage = new Message();
        lastMessage.setMessageText("Test message");
        dialog.setMessages(Collections.singletonList(lastMessage));

        DialogDto result = MapperDialogToDto.convertDialogToDto(dialog);

        assertNotNull(result, "Result should not be null");

        assertNotNull(result.getLastMessage(), "Last message should not be null");
        assertEquals(lastMessage.getMessageText(), result.getLastMessage().getMessageText(), "Message text should match");

        assertEquals(dialog.getId(), result.getId(), "ID should match");
        assertEquals(dialog.getUnreadCount(), result.getUnreadCount(), "Unread count should match");
    }

    @Test
    void testConvertDialogToDto_WithNullDialog() {
        // Act
        DialogDto dialogDto = MapperDialogToDto.convertDialogToDto(null);

        // Assert
        assertNull(dialogDto);
    }

    @Test
    public void testConvertDialogToDto_WithEmptyMessages() {
        Dialog dialog = new Dialog();
        dialog.setMessages(Collections.emptyList());
        dialog.setId(1L);
        dialog.setUnreadCount(0L);

        DialogDto result = MapperDialogToDto.convertDialogToDto(dialog);

        assertNotNull(result, "Result should not be null");

        assertNull(result.getLastMessage(), "Last message should be null when messages are empty");

        assertEquals(dialog.getId(), result.getId(), "ID should match");
        assertEquals(dialog.getUnreadCount(), result.getUnreadCount(), "Unread count should match");
    }




}
