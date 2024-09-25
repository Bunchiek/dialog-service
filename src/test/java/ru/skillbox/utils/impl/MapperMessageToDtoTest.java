//package ru.skillbox.utils.impl;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.*;
//
//import org.junit.jupiter.api.Test;
//import ru.skillbox.dto.MessageDto;
//import ru.skillbox.entity.Account;
//import ru.skillbox.entity.Message;
//import ru.skillbox.entity.Status;
//
//import java.time.Instant;
//import java.util.UUID;
//
//public class MapperMessageToDtoTest {
//
//    @Test
//    void testConvertMessageToDto_WithValidMessage() {
//        // Arrange
//        Account author = mock(Account.class);
//        Account recipient = mock(Account.class);
//        when(author.getId()).thenReturn(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"));
//        when(recipient.getId()).thenReturn(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"));
//
//        Message message = mock(Message.class);
//        when(message.getId()).thenReturn(1L);
//        when(message.getTime()).thenReturn(Instant.now().toEpochMilli());
//        when(message.getAuthor()).thenReturn(author);
//        when(message.getRecipient()).thenReturn(recipient);
//        when(message.getMessageText()).thenReturn("Hello");
//        when(message.getStatus()).thenReturn(Status.SENT);
//
//        // Act
//        MessageDto messageDto = MapperMessageToDto.convertMessageToDto(message);
//
//        // Assert
//        assertEquals("Hello", messageDto.getMessageText());
//        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"), messageDto.getAuthorId());
//        assertEquals(UUID.fromString("550e8400-e29b-41d4-a716-446655440001"), messageDto.getRecipientId());
//        assertEquals(Status.SENT, messageDto.getStatus());
//    }
//
//    @Test
//    void testConvertMessageToDto_WithNullMessage() {
//        // Act
//        MessageDto messageDto = MapperMessageToDto.convertMessageToDto(null);
//
//        // Assert
//        assertNull(messageDto);
//    }
//}
