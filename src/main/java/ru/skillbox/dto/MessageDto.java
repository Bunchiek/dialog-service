package ru.skillbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skillbox.entity.Status;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto  {

    private Long id;
    private Long time;
    private UUID authorId;
    private UUID recipientId;
    private String messageText;
    private Status status;

}
