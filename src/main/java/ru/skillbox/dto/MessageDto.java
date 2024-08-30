package ru.skillbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto  {


    private Long time;
    private Long authorId;
    private Long recipientId;
    private String messageText;
    private Long dialogId;

    public MessageDto(String messageText) {
        this.messageText = messageText;
    }
}
