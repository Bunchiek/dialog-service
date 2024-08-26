package ru.skillbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.entity.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {

    private Long id;
    private Long time;
    private Long authorId;
    private Long recipientId;
    private String messageText;
    private Status status;

}
