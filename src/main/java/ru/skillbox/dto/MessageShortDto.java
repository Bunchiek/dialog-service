package ru.skillbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageShortDto {

    private Long id;
    private Long authorId;
    private Long time;
    private String messageText;

}
