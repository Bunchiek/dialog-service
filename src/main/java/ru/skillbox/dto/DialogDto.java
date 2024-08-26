package ru.skillbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DialogDto {

    private Long id;
    private Long unreadCount;
    private AccountDto conversationPartner;
    private MessageDto lastMessage;
}
