package ru.skillbox.utils.impl;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.skillbox.dto.ShortMessageForDialogDto;
import ru.skillbox.entity.Message;
import ru.skillbox.entity.Status;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MapperMessageToDialogMessageDTOTest {

    @Test
    void convertMessageToDto_shouldReturnCorrectDto() {
        // Мокируем объект Message
        Message message = Mockito.mock(Message.class);

        // Настраиваем поведение мок-объекта
        LocalDateTime time = LocalDateTime.of(2024, 10, 6, 12, 0);
        UUID authorId = UUID.randomUUID();
        String messageText = "Hello, this is a test message";
        Status status = Status.SENT;

        Mockito.when(message.getTime()).thenReturn(time);
        Mockito.when(message.getAuthor()).thenReturn(authorId);
        Mockito.when(message.getMessageText()).thenReturn(messageText);
        Mockito.when(message.getStatus()).thenReturn(status);

        // Вызываем метод для тестирования
        ShortMessageForDialogDto dto = MapperMessageToDialogMessageDTO.convertMessageToDto(message);

        // Проверяем, что DTO не null
        assertNotNull(dto);

        // Проверяем, что поля корректно сконвертировались
        assertEquals(time, dto.getTime());
        assertEquals(authorId, dto.getAuthorId());
        assertEquals(messageText, dto.getMessageText());
        assertEquals(status, dto.getReadStatus());
    }

    @Test
    void convertMessageToDto_shouldReturnNullWhenMessageIsNull() {
        // Проверка на случай, если передается null
        ShortMessageForDialogDto dto = MapperMessageToDialogMessageDTO.convertMessageToDto(null);

        // Проверяем, что возвращается null
        assertNull(dto);
    }
}
