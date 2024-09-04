package ru.skillbox.controller;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.dto.GetDialogsRs;
import ru.skillbox.dto.GetMessagesRs;
import ru.skillbox.dto.SetStatusMessageReadRs;
import ru.skillbox.dto.UnreadCountRs;
import ru.skillbox.service.DialogService;

@RestController
@RequestMapping("/api/v1/dialogs")
@RequiredArgsConstructor
public class DialogController {

    private final DialogService dialogService;

    @PutMapping("/{companionId}")
    public ResponseEntity<SetStatusMessageReadRs> setStatusMessageRead(@PathVariable Long companionId) {
        SetStatusMessageReadRs response = dialogService.setStatusMessageRead(companionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GetDialogsRs> getAllDialogs(
            @RequestParam(value = "offset", defaultValue = "0", required=false) Integer offset,
            @RequestParam(value = "itemPerPage", defaultValue = "20", required = false) Integer itemPerPage) {
        GetDialogsRs response = dialogService.getAllDialogs(PageRequest.of(offset, itemPerPage));
        return ResponseEntity.ok(response);
    }


    @GetMapping("/unread")
    public ResponseEntity<UnreadCountRs> getUnreadMessageCount() {
        UnreadCountRs response = dialogService.getUnreadMessageCount();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/messages")
    public ResponseEntity<GetMessagesRs> getAllMessages(
            @RequestParam("companionId") Long companionId,
            @RequestParam(value = "offset", defaultValue = "0", required=false) int offset,
            @RequestParam(value = "itemPerPage", defaultValue = "20",required=false) int itemPerPage) {
        GetMessagesRs response = dialogService.getAllMessages(companionId, PageRequest.of(offset, itemPerPage));
        return ResponseEntity.ok(response);
    }


}
