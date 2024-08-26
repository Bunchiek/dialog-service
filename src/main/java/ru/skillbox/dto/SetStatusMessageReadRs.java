package ru.skillbox.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SetStatusMessageReadRs {

    private String error;
    private Long timestamp;
    private SetStatusMessageReadDto data;
    private String errorDescription;


}
