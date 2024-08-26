package ru.skillbox.service;


import org.springframework.data.domain.Pageable;
import ru.skillbox.dto.GetDialogsRs;
import ru.skillbox.dto.GetMessagesRs;
import ru.skillbox.dto.SetStatusMessageReadRs;
import ru.skillbox.dto.UnreadCountRs;

public interface DialogService {

    public SetStatusMessageReadRs setStatusMessageRead(Long companionId);

    public GetDialogsRs getAllDialogs(Pageable pageable);

    public UnreadCountRs getUnreadMessageCount();

    public GetMessagesRs getAllMessages(Long companionId, Pageable pageable);

}
