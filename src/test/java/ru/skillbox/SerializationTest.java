package ru.skillbox;

import org.junit.jupiter.api.Test;
import ru.skillbox.dto.MessageDto;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializationTest {

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        MessageDto originalMessage = new MessageDto();
        originalMessage.setMessageText("test value");
        originalMessage.setTime(123123L);
        originalMessage.setAuthorId(1L);
        originalMessage.setDialogId(2L);
        originalMessage.setRecipientId(2L);// Установите значения полей

        // Сериализация
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(originalMessage);
        oos.flush();
        byte[] serializedMessage = baos.toByteArray();

        // Десериализация
        ByteArrayInputStream bais = new ByteArrayInputStream(serializedMessage);
        ObjectInputStream ois = new ObjectInputStream(bais);
        MessageDto deserializedMessage = (MessageDto) ois.readObject();

        // Проверка
        assertEquals(originalMessage.getMessageText(), deserializedMessage.getMessageText());
    }
}
