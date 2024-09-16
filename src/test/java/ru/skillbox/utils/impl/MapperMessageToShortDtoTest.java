package ru.skillbox.utils.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ru.skillbox.dto.MessageShortDto;
import ru.skillbox.entity.Account;
import ru.skillbox.entity.Message;

import java.util.UUID;

public class MapperMessageToShortDtoTest {

    @Test
    public void testConvertMessageToShortDto_WithValidMessage() {
        Account author = new Account();
        author.setId(UUID.randomUUID());

        Message message = new Message();
        message.setAuthor(author);
        message.setMessageText("Test message");

        MessageShortDto result = MapperMessageToShortDto.convertMessageToShortDto(message);

        assertNotNull(result, "Result should not be null");
        assertEquals(author.getId(), result.getAuthorId(), "Author ID should match");
        assertEquals("Test message", result.getMessageText(), "Message text should match");
    }

    @Test
    void testConvertMessageToShortDto_WithNullMessage() {
        // Act
        MessageShortDto messageShortDto = MapperMessageToShortDto.convertMessageToShortDto(null);

        // Assert
        assertNull(messageShortDto);
    }

    @Test
    public void testConvertMessageToShortDto_WithNullAuthor() {
        Message message = new Message();
        message.setAuthor(null);
        message.setMessageText("Test message");

        MessageShortDto result = MapperMessageToShortDto.convertMessageToShortDto(message);

        assertNotNull(result, "Result should not be null");
        assertNull(result.getAuthorId(), "Author ID should be null when author is null");
        assertEquals("Test message", result.getMessageText(), "Message text should match");
    }
}
