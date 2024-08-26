package ru.skillbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnreadCountRs {

    private String error;
    private Long timestamp;
    private UnreadCountDto data;
    private String errorDescription;
}
